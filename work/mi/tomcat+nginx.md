# nginx + tomcat 的一次调优
## 背景
使用nginx反向代理到tomcat请求，tomcat 主要是处理静态资源，通过tomcat 解析用户的
cookie 拿到用户id，通过id 进行灰度，根据灰度结果，选择不同分支的静态资源，返回给nginx。
灰度的维度是资源项目，灰度结果是资源项目的git分支。
tomcat 是springboot项目的内置容器
## tomcat 配置
### yaml文件配置
```
tomcat:
    threads:
      #最大线程数  默认200
      max: 200
      # 最小线程数 默认25
      min-spare: 25 
      #最大链接数
      max-connections: 8192
      # 最大连接数，满了之后排队的请求,
      accept-count: 100
      # 日志配置
    accesslog:
      enabled: true
      directory: ${log.file.path}
      #  日志格式
      pattern: '%{yyyy-MM-dd_HH:mm:ss.SSS}t %A %m %U%q %s cost %D %I bytes %B'
      # 缓存
      buffered: true
      # 日志轮转，根据日志的timestamp 和 file-data-format 把access.log重命名为一个文件，避免所有日志都写入同一个文件，造成超级大文件
      rename-on-rotate: true
      rotate: true
      file-date-format: .yyyyMMdd # Date format to place in log file name.
      prefix: access.log
      suffix: ""

```
### keepalive配置
由于ngixn反向代理到tomcat的,请求都是短请求，tomcat 基本是1毫秒就可以处理完成，但是通过观察
发现整个请求的耗时达到15ms，通过分析tcp请求，发现nginx到tomcat的每个请求都会重新建立一次tcp
请求，主要的耗时就在建立tcp连接上了，每次建立tcp链接需要10ms，所以需要设置nginx到tomcat的长连接
让多个请求，共用同一个tco连接。这里需要使用keepalive进行配置，
keepalive一般都是有两个属性：主要其中一个属性满足条件，则tcp连接会被删除，重建
1, KeepAliveTimeout  最长保持时间
2, MaxKeepAliveRequests  , 最多可以被多少个请求复用
#### 注意：
由于使用nginx作为反向代理，需要nginx端也要设置keepAlive才能生效，而且nginx端也可以删除
tcp连接，所以tomcat属性值的必须比nginx大，由nginx来决定何时重建tcp连接。如果tomcat
设置的比nginx小，则会导致nginx连接被动中断，然后导致不确定的事情发生。
#### 由于springboot 不支持直接设置
```
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

/**
 * 由于一些配置无法通过配置文件进行配置
 * 所以这里是对tomcat的特殊处理
 * 主要是为了让tomcat 和 nginx 之间保持长连接，对tcp 进行复用, 减少响应时间
 */
@Slf4j
@Component
class AppTomcatConnectorCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

  @Override
  public void customize(ConfigurableServletWebServerFactory factory) {
    TomcatServletWebServerFactory tomcatFactory = (TomcatServletWebServerFactory) factory;
    tomcatFactory.setProtocol("org.apache.coyote.http11.Http11Nio2Protocol");
    tomcatFactory.addConnectorCustomizers(connector -> {
      AbstractHttp11Protocol<?> protocol = (AbstractHttp11Protocol<?>)connector.getProtocolHandler();
      // keepalive保持时间
      protocol.setKeepAliveTimeout(600000);
      // keepalive可以被多少个请求复用
      protocol.setMaxKeepAliveRequests(1000000);
      log.info("Tomcat({})  -- MaxConnection:{};MaxThreads:{};MinSpareThreads:{},getMaxKeepAliveRequests {}, getKeepAliveTimeout {} ",
              protocol.getClass().getName(),
              protocol.getMaxConnections(),
              protocol.getMaxThreads(),
              protocol.getMinSpareThreads(),
              protocol.getMaxKeepAliveRequests(),
              protocol.getKeepAliveTimeout()
              );
    });
  }
}


```
## nginx 配置
### upstream keepalive配置
upstream keepalive 中的keepalive只会对upstream生效，也就是
只对tomcat生效，不会对其他upstream 或者用户的keepalive生效。
如果想对客户端到nginx之间的tcp设置keepalive则需要设置全局的keepalive就可以
```
# 全局keepalive，客户端到ngixnkeepalive生效
keepalive_requests 10000;
keepalive_timeout  120s;
upstream test_backend {
    server 127.0.0.1:29999;
    # 只对 tomcat请求生效
    keepalive_requests 10000;
    keepalive_timeout  120s;
    keepalive 100;
}
```
## 缓存配置
由于做了灰度服务，所以不能在nginx层做缓存，需要在tomcat 做缓存，tomcat支持 lastModifu
和 etag两种缓存策略，lastModify 是根据文件的最后修改时间精确到秒判断缓存是否生效。由于目前
tomcat都是分布式部署，所以静态资源文件lastModify不同机器是不一样的，所以需要同时使用etag
缓存，当lastModify失效之后，使用文件的md5值进行文件是否被修改的判断。通过缓存配置基本可以
实现25%缓存命中，即http请求返回304状态码，响应时间进一步可以降低.
springboot项目都是通过Filter进行配置，由于Filter的先进后出的特性，所以etag优先级
高于lastModify,etag里面会判断状态码是否是304，如果304直接返回，否则进行hash值校验。
### etag 配置
```
/**
 * Filte 配置
 */
@Configuration
public class FilterConfig {
 /**
   * Etag
   * @return
   */
  @Bean
  public FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean () {
    // ShallowEtagHeaderFilter 这个是springboot 提供的etag实现
    ShallowEtagHeaderFilter eTagFilter = new ShallowEtagHeaderFilter();
    // 大小写不敏感, 由于不同的浏览器肯能会导致不同的情况，所以这里使用大小写不敏感，兼容所有情况
    eTagFilter.setWriteWeakETag(true);
    FilterRegistrationBean<ShallowEtagHeaderFilter> registration = new FilterRegistrationBean();
    registration.setFilter(eTagFilter);
    registration.addUrlPatterns("/*");
     // 优先级一定要是最高的 
    registration.setOrder(1);
    return registration;
  }
}

```
### lastModify配置
```

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.concurrent.TimeUnit;

@EnableWebMvc
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
     //url 和 静态资源文件的映射 可以通过url获取静态资源
    registry.addResourceHandler("/static/**")
            .addResourceLocations("file:/home/static/")
            // 设置 lastModofy 缓存配置
            .setCacheControl(CacheControl.maxAge(3, TimeUnit.MINUTES).mustRevalidate().cachePrivate())
            .resourceChain(true);
  }
}
```
## gzip压缩
经过测试发现静态资源文件大小普遍大于200k，而200k的文件，在传输过程中耗时较多（从nginx到客户端）
tomcat->nginx是内网很快。所以需要对资源文件进行gzip压缩，减少传输时间。
由于nginx是统一的对外服务，所以通过在nginx层做gzip压缩。
开启压缩之后响应时间基本降低了3倍左右，根据实际情况不同，会不一样。

## 测试工具
abTest 
https://httpd.apache.org/docs/2.4/programs/ab.html

```
// -C 表示cookie -n 请求总数 -c 并发数
ab -k -n 100000 -c 100 -C "id=110" http://localhost:29999/test.html

// -b 表示cookie
curl -b  "id=110" http://localhost:29999/test.html
```
## 统计工具
ab test的缺点是，大量请求发出去之后，接收请求是慢，导致统计结果不准确，所以单独写了一个
脚本根据nginx输出的upstream request time 统计tomcat处理时间，
## 总结
10万个请求，P95从最初的15ms，降低到1ms，这就是优化的结果。
当然项目部署之后，还有一些情况，导致tomcat锁死的情况，通过jstack
打印堆栈解决了， 下一篇会讲到。
