# maven依赖解决冲突
key: maven、 依赖冲突界巨头
maven的依赖冲突主要分为两种，一种是相同的jar包
版本不一致冲突。
第二是是不同的jar包之间，由于类名一致，包路径也一直，导致的冲突。

## 版本不一致导致的冲突
通过idea可以直接点击冲突的类，可以直接展示jar包的版本号，通过
这个确定，自己需要的版本号是多少，然后在pom文件中添加一下配置
就可以确保所以依赖jar包的版本保持一致，不需要exclude
```
<dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.conflict</groupId>
                <artifactId>conflict-jar</artifactId>
                <version>1.0.1</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
```
## 不同jar包之间的冲突
典型例子log4g 和 logback 日志实现发生了冲突，这个时候可以通过
使用 @Slf4j注解获取log实例 , 在本地直接使用main方法运行
下面的方法，获取当前log的类，就可以确定是那个类提供的log实现
，然后exclude掉该jar就可以了。
```
System.out.println("======" + log.getClass());
```
### 有的时候不好找打冲突的类，
这个时候可以先把服务打包，然后使用类的全路径,使用 grep className package.jar
的方式，就可以获取所有包含该类的jar包，通过这种方式获取冲突的jar包， 然后把不需要的
exclude掉。