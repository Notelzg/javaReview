# dubbo ExtensionLoader 详解

dubbo SPI 对比SPring的SPI实现了
IoC(自动注入)、 AOP（切面，使用Wrap 包装类通过装饰器模式实现）, 根据条件使用相应的服务
主要注解
@Adaptive 实现了自动适配, 同一个接口的多个实现类，但是每次调用的时候只能使用其中的一个
@Active  主要用于AOP，实现了自动激活，一次调用，可以使用多个实现类似于AOP，可以排序, 有group ， Before， after
@Wrapper 封装类类似于AOP可以排序, 主要通过其构造函数的参数类型是自己实现的接口进行判断

## 初始化ExtensionLoader 
ExtensionLoader  对象的获取只能通过 getExtensionLoader 方法，
因为其构造函数是私有的，getExtensionLoader是静态方法使用起来更方便
这里会进行缓存，保证每个接口只实例化一个 ExtensionLoader 

```
// 使用方式
SimpleExt ext = ExtensionLoader.getExtensionLoader(SimpleExt.class).getAdaptiveExtension();

 // 构造函数私有化，禁止单独获取，因为要缓存该对象，所以禁止
 //  @param type 是接口类，每个接口都有一个ExtensionLoader
private ExtensionLoader(Class<?> type) {
        this.type = type;
        objectFactory = (type == ExtensionFactory.class ? null : ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension());
    }

// 通过以下方法获取ExtensionLoader 对象, 并且缓存，保存只初始化一份
 public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Extension type == null");
        }
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Extension type (" + type + ") is not an interface!");
        }
        if (!withExtensionAnnotation(type)) {
            throw new IllegalArgumentException("Extension type (" + type +
                    ") is not an extension, because it is NOT annotated with @" + SPI.class.getSimpleName() + "!");
        }

        ExtensionLoader<T> loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        if (loader == null) {
            EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<T>(type));
            loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        }
        return loader;
    }
```
## objectFactory 获取对象的工厂类
```
objectFactory = (type == ExtensionFactory.class ? null : ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension());

#   ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension());
#  主要是通过 ExtensionFactory接口获取AdaptiveExtensionFactory这个类的注解是 Adaptive所以
获取的对象是AdaptiveExtensionFactory
```
###  AdaptiveExtensionFactory 分析
```java

public class AdaptiveExtensionFactory implements ExtensionFactory {

    private final List<ExtensionFactory> factories;

   // 获取所有实现 ExtensionFactory 接口的类 SpiExtensionFactory、 AdaptiveExtensionFactory、SpringExtensionFactory
    public AdaptiveExtensionFactory() {
        ExtensionLoader<ExtensionFactory> loader = ExtensionLoader.getExtensionLoader(ExtensionFactory.class);
        List<ExtensionFactory> list = new ArrayList<ExtensionFactory>();
        for (String name : loader.getSupportedExtensions()) {
            list.add(loader.getExtension(name));
        }
        factories = Collections.unmodifiableList(list);
    }
    
//  遍历类工厂，获取对象，对象不为空则返回
    @Override
    public <T> T getExtension(Class<T> type, String name) {
        for (ExtensionFactory factory : factories) {
            T extension = factory.getExtension(type, name);
            if (extension != null) {
                return extension;
            }
        }
        return null;
    }

}

```
###  getSupportedExtensions
```
public Set<String> getSupportedExtensions() {
        Map<String, Class<?>> clazzes = getExtensionClasses();
        return Collections.unmodifiableSet(new TreeSet<>(clazzes.keySet()));
    }
```

## 获取类加载策略器 loadLoadingStrategies
dubbo的SPI实现的基础是 LoadingStrategy 这接口，这个接口通过ServiceLoader加载所有
实现类，目前有
DubboInternalLoadingStrategy 加载目录 "META-INF/dubbo/internal/  下的文件
DubboLoadingStrategy   加载目录是  "META-INF/dubbo/"  下的文件
ServicesLoadingStrategy  加载目录  META-INF/services/ 下的文件
### 类加载器初始化
```
// dubbo 通过侧率模式加载不通目录的类，其策略类通过 通过ServiceLoader加载 
private static volatile LoadingStrategy[] strategies = loadLoadingStrategies();

private static LoadingStrategy[] loadLoadingStrategies() {
        return stream(ServiceLoader.load(LoadingStrategy.class).spliterator(), false)
                .sorted()
                .toArray(LoadingStrategy[]::new);
    }
```
### 获取接口的所有实现类，getExtensionClasses
```
private Map<String, Class<?>> getExtensionClasses() {
        Map<String, Class<?>> classes = cachedClasses.get();
        if (classes == null) {
            synchronized (cachedClasses) {
                classes = cachedClasses.get();
                if (classes == null) {
                    classes = loadExtensionClasses();
                    cachedClasses.set(classes);
                }
            }
        }
        return classes;
    }
```
### 遍历类加载器加载所有的实现类， loadExtensionClasses 
```
 private Map<String, Class<?>> loadExtensionClasses() {
        cacheDefaultExtensionName();

        Map<String, Class<?>> extensionClasses = new HashMap<>();

        for (LoadingStrategy strategy : strategies) {
             // 这里是可以跨越jar包获取所有的实现类，只有路径对，不管在哪个jar里面，其文件都会被获取到
            loadDirectory(extensionClasses, strategy.directory(), type.getName(), strategy.preferExtensionClassLoader(), strategy.overridden(), strategy.excludedPackages());
            loadDirectory(extensionClasses, strategy.directory(), type.getName().replace("org.apache", "com.alibaba"), strategy.preferExtensionClassLoader(), strategy.overridden(), strategy.excludedPackages());
        }

        return extensionClasses;
    }
```
## 
通过策略模式，实现不同目录下类的加载，从而把dubbo的实现类进行了隔离通过目录进行隔离，
如果有新的目录可以通过实现该接口进行扩展。
## 根据条件使用相应的服务 getAdaptiveExtension
主要通过接口名称，加载器所有实现类，如果实现类中有类注解@Adaptive 则实例化该类，直接返回
如果没有类注解， 则判断该接口是否有方法注解，如果没有则抛出异常，有则生成 interface$adaptive
类，该类会根据Adaptive注解的value作为key去获取url中的value 如果url中不存在该key，则用，@SPI接口上的value作为默认值.
###  先从缓存获取, 没有则生成
```
  public T getAdaptiveExtension() {
     Object instance = this.cachedAdaptiveInstance.get();
 
     // 单例模式的，二次检查， 第一次检查
     if (instance == null) {
       if (this.createAdaptiveInstanceError != null) {
         throw new IllegalStateException("Failed to create adaptive instance: " + this.createAdaptiveInstanceError.toString(), this.createAdaptiveInstanceError);
       }
       synchronized(this.cachedAdaptiveInstance) {
         // 单例模式的，二次检查， 第二次检查, 如果第一次检测有多个线程通过， 则获取锁之后需要再检查一次
         // 这是因为第一个获取锁的线程会生成对象，释放锁之后，防止其他线程会再次创建对象, 保证单例
         instance = this.cachedAdaptiveInstance.get();
         if (instance == null) {
           try {
            // 生成
             instance = this.createAdaptiveExtension();
             this.cachedAdaptiveInstance.set(instance);
           } catch (Throwable var5) {
             this.createAdaptiveInstanceError = var5;
             throw new IllegalStateException("Failed to create adaptive instance: " + var5.toString(), var5);
           }
         }
       }
     }
 
     return instance;
   }
```

### 生成适配对象
```
  private T createAdaptiveExtension() {
         try {
             // injectExtension  实现了依赖自动注入的功能，只要有setInterfaceName(Interface face ) 
             return injectExtension((T) getAdaptiveExtensionClass().newInstance());
         } catch (Exception e) {
             throw new IllegalStateException("Can't create adaptive extension " + type + ", cause: " + e.getMessage(), e);
         }
     }

 // 获取适配器类
 private Class<?> getAdaptiveExtensionClass() {
    // 这个方法获取实现类的时候，如果实现类的注解是 @Adaptive 则，设置为this.cachedAdaptiveClass
    this.getExtensionClasses();
    // 注解类存在则直接返回，否则根据 接口的@Aaptive注解生成一个适配器类，这个适配器类会根据url中的参数选择合适的服务
    return this.cachedAdaptiveClass != null ? this.cachedAdaptiveClass : (this.cachedAdaptiveClass = this.createAdaptiveExtensionClass());
  }

```

### 根据@Adaptive注解，接口方法生成的适配器类
```
//接口定义 
@SPI("impl1")
public interface SimpleExt {
  // 方法注解
  @Adaptive
  String echo(URL url, String s);
}


// 下面是一个根据方法注解生成的适配器类
package org.apache.dubbo.samples.provider;
import org.apache.dubbo.common.extension.ExtensionLoader;
public class SimpleExt$Adaptive implements org.apache.dubbo.samples.provider.SimpleExt {
  
  // 添加 @Adaptive注解的接口方法
  public java.lang.String echo(org.apache.dubbo.common.URL arg0, java.lang.String arg1) {
    if (arg0 == null) throw new IllegalArgumentException("url == null");
    org.apache.dubbo.common.URL url = arg0;
    // 这里获取适配器类，simple.ext 是 @Adaptive注解的值，impl1 是 SPI注解的值
    String extName = url.getParameter("simple.ext", "impl1");
    if (extName == null)
      throw new IllegalStateException("Failed to get extension (org.apache.dubbo.samples.provider.SimpleExt) name from url (" + url.toString() + ") use keys([simple.ext])");
    // 根据ExtensionLoader的 getExtension（extName)  获取适配器类的对象
    org.apache.dubbo.samples.provider.SimpleExt extension = (org.apache.dubbo.samples.provider.SimpleExt) ExtensionLoader.getExtensionLoader(org.apache.dubbo.samples.provider.SimpleExt.class).getExtension(extName);
    //调用方法
    return extension.echo(arg0, arg1);
  }
}
```
###  IOC  依赖反转，依赖自动注入 
这个只能注入，Adaptive类， 或者 Spring对象，但是需要配置spring配置文件
```
   private T injectExtension(T instance) {

        if (objectFactory == null) {
            return instance;
        }

        try {
            // 遍历方法 
            for (Method method : instance.getClass().getMethods()) {
                if (!isSetter(method)) {
                    continue;
                }
                /**
                 * Check {@link DisableInject} to see if we need auto injection for this property
                 */
               
                if (method.getAnnotation(DisableInject.class) != null) {
                    continue;
                }
                Class<?> pt = method.getParameterTypes()[0];
                if (ReflectUtils.isPrimitives(pt)) {
                    continue;
                }

                try {
                    String property = getSetterProperty(method);
                    Object object = objectFactory.getExtension(pt, property);
                    if (object != null) {
                        method.invoke(instance, object);
                    }
                } catch (Exception e) {
                    logger.error("Failed to inject via method " + method.getName()
                            + " of interface " + type.getName() + ": " + e.getMessage(), e);
                }

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return instance;
    }
```
## 根据服务名称获取其实现类实现依赖注入的功能 getExtension
具体实现
```
  public T getExtension(String name) {
        T extension = getExtension(name, true);
        if (extension == null) {
            throw new IllegalArgumentException("Not find extension: " + name);
        }
        return extension;
    }

    public T getExtension(String name, boolean wrap) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Extension name == null");
        }
        if ("true".equals(name)) {
            // 默认实现，就是接口 SPI注解的value        
            return getDefaultExtension();
        }
        final Holder<Object> holder = getOrCreateHolder(name);
        Object instance = holder.get();
         // 单例
        if (instance == null) {
            synchronized (holder) {
                instance = holder.get();
                if (instance == null) {
                    // 生成对象
                    instance = createExtension(name, wrap);
                    holder.set(instance);
                }
            }
        }
        return (T) instance;
    }
```
### 创建对象
```
    private T createExtension(String name, boolean wrap) {
        // 获取其类
        Class<?> clazz = getExtensionClasses().get(name);
        if (clazz == null || unacceptableExceptions.contains(name)) {
            throw findException(name);
        }
        try {
            T instance = (T) EXTENSION_INSTANCES.get(clazz);
            if (instance == null) {
                // 实例化对象
                EXTENSION_INSTANCES.putIfAbsent(clazz, clazz.getDeclaredConstructor().newInstance());
                instance = (T) EXTENSION_INSTANCES.get(clazz);
            }
            // 注入依赖
            injectExtension(instance);


            // 封装类
            if (wrap) {

                List<Class<?>> wrapperClassesList = new ArrayList<>();
                if (cachedWrapperClasses != null) {
                    wrapperClassesList.addAll(cachedWrapperClasses);
                    // 排序
                    wrapperClassesList.sort(WrapperComparator.COMPARATOR);
                    // 优先级最高的在最内层,被调用
                    Collections.reverse(wrapperClassesList);
                }

                // 这里进行循环初始化封装类, 
                if (CollectionUtils.isNotEmpty(wrapperClassesList)) {
                    for (Class<?> wrapperClass : wrapperClassesList) {
                        Wrapper wrapper = wrapperClass.getAnnotation(Wrapper.class);
                        if (wrapper == null
                                || (ArrayUtils.contains(wrapper.matches(), name) && !ArrayUtils.contains(wrapper.mismatches(), name))) {
                            instance = injectExtension((T) wrapperClass.getConstructor(type).newInstance(instance));
                        }
                    }
                }
            }

            //初始化Lifecycle
            initExtension(instance);
            return instance;
        } catch (Throwable t) {
            throw new IllegalStateException("Extension instance (name: " + name + ", class: " +
                    type + ") couldn't be instantiated: " + t.getMessage(), t);
        }
    }
```
## 切面实现 getActiveExtension
```
public List<T> getActivateExtension(URL url, String key, String group) {
        String value = url.getParameter(key);
        return getActivateExtension(url, StringUtils.isEmpty(value) ? null : COMMA_SPLIT_PATTERN.split(value), group);
    }

  /**
     * Get activate extensions.
     *
     * @param url    url
     * @param values extension point names
     * @param group  group
     * @return extension list which are activated
     * @see org.apache.dubbo.common.extension.Activate
     */
    public List<T> getActivateExtension(URL url, String[] values, String group) {
        // solve the bug of using @SPI's wrapper method to report a null pointer exception.
        Map<Class<?>, T> activateExtensionsMap = new TreeMap<>(ActivateComparator.COMPARATOR);
        List<String> names = values == null ? new ArrayList<>(0) : asList(values);
        if (!names.contains(REMOVE_VALUE_PREFIX + DEFAULT_KEY)) {
            if (cachedActivateGroups.size() == 0) {
                synchronized (cachedActivateGroups) {
                    // cache all extensions
                    if (cachedActivateGroups.size() == 0) {
                        // 获取实现类, 加载类的时候，如果类是active注解，则添加到缓存中: cachedActivates 
                        getExtensionClasses();
                        for (Map.Entry<String, Object> entry : cachedActivates.entrySet()) {
                            String name = entry.getKey();
                            Object activate = entry.getValue();

                            String[] activateGroup, activateValue;

                            if (activate instanceof Activate) {
                                activateGroup = ((Activate) activate).group();
                                activateValue = ((Activate) activate).value();
                            // dubbo 没有捐赠到Apache之前，其报名是 com.alibaba.dubbo.common
                            } else if (activate instanceof com.alibaba.dubbo.common.extension.Activate) {
                                activateGroup = ((com.alibaba.dubbo.common.extension.Activate) activate).group();
                                activateValue = ((com.alibaba.dubbo.common.extension.Activate) activate).value();
                            } else {
                                continue;
                            }
                            cachedActivateGroups.put(name, new HashSet<>(Arrays.asList(activateGroup)));
                            cachedActivateValues.put(name, activateValue);
                        }
                    }
                }
            }

            // traverse all cached extensions
            cachedActivateGroups.forEach((name, activateGroup)->{
                if (isMatchGroup(group, activateGroup)
                        && !names.contains(name)
                        && !names.contains(REMOVE_VALUE_PREFIX + name)
                        && isActive(cachedActivateValues.get(name), url)) {

                    activateExtensionsMap.put(getExtensionClass(name), getExtension(name));
                }
            });
        }

        if (names.contains(DEFAULT_KEY)) {
            // will affect order
            // `ext1,default,ext2` means ext1 will happens before all of the default extensions while ext2 will after them
            ArrayList<T> extensionsResult = new ArrayList<>(activateExtensionsMap.size() + names.size());
            for (int i = 0; i < names.size(); i++) {
                String name = names.get(i);
                if (!name.startsWith(REMOVE_VALUE_PREFIX)
                    && !names.contains(REMOVE_VALUE_PREFIX + name)) {
                    if (!DEFAULT_KEY.equals(name)) {
                        if (containsExtension(name)) {
                            extensionsResult.add(getExtension(name));
                        }
                    } else {
                        extensionsResult.addAll(activateExtensionsMap.values());
                    }
                }
            }
            return extensionsResult;
        } else {
            // add extensions, will be sorted by its order
            for (int i = 0; i < names.size(); i++) {
                String name = names.get(i);
                if (!name.startsWith(REMOVE_VALUE_PREFIX)
                    && !names.contains(REMOVE_VALUE_PREFIX + name)) {
                    if (!DEFAULT_KEY.equals(name)) {
                        if (containsExtension(name)) {
                            activateExtensionsMap.put(getExtensionClass(name), getExtension(name));
                        }
                    }
                }
            }
            return new ArrayList<>(activateExtensionsMap.values());
        }
    }
```
## compile 
Adaptive注解的类是生成的，不是用的反射技术，主要原因是因为生成的java运行速度更快
