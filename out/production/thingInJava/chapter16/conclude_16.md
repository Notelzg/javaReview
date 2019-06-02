# 数组
## 定义一个数组
```
        Object[][] o = new Object[10][2];
        o[0] = new Object[]{"key", "value"};
        // 和上面的定义方式一样，目前没有发现区别
        Object[] t = {"1", "2"} ;
        o[1] = t;
        Arrays.deepToString(o);
        System.out.println(Arrays.toString(t));
        System.out.println(Arrays.deepToString(o));

```
## Arrays工具类
### 
把一个数组转成ArrayList，但是这个List是一个final类型，不能再被进行改变其size大小的操作
比如，add，clear，remove等改变list size的操作，都会是不支持的，因为源码是这样的定义的
        private final E[] a;
如上所示，定义了一个final类型的a的引用，这个a是无法改变的，所以就不能改变a的长度，而且
Arrays.asList中，的ArrayList是自己定义的实现的，本身就没有提供add addAll clean等方法
的重载，其实可以去看看这个asList方法，因为这里面就用到了，自定义的List，继承了AbstractList.
asList返回的ArrayList是一个内部的自定义的private ArrayList，如果你用java.util.ArrayList来接收
就会出现类型不兼容的错误，由于ArrayList是privat的所以只能向上转型Collection/List.
        
 
Arrays.asList
输出一维数组的所有值
Arrays.toString
输出多维数组值
Arrays.toDeepString
# 总结
  通过看java编程思想，同时去阅读源码，就会发现源码也不一定都是最好的，因为随着jdk的发展，总要出现
高版本去适配低版本，就会导致jdk中出现妥协代码，这个时候可能就会导致我们看不懂，或者看的莫名其妙，这个
时候不要担心，是自己能力的问题，可能他就是为了兼容所以处理的，当然也有可能是编写者的一个错误，只是这
个错误没有导致不良的后果而已。
    是不是会产生那还看源码作甚呢, 看源码的过程就是一个认识java设计思想的过程，也是一个解惑的过程
也是一个揭开编程语言神秘面纱的过程，是不是很激动，这是一个知其所以然的过程，虽然他可能花费你很长的时间
而你也不能从其中获益，但是就像别人说的，学习本来就是一个螺旋式上升的过程，虽然你不能立即获益，但是从
长远来看一定是有益的，至于是什么，那估计就是装逼的资本哈哈。    
