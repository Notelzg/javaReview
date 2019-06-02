# chapter17 容器深入研究
## Map 分析
首先Map<k,v>是一个interface,jdk 源码声明：
    public interface Map<K,V> 
AbstractMap<k,v> 是一个抽象类实现Map 源码声明：
    public abstract class AbstractMap<K,V> implements Map<K,V> 
AbstractMap 本身已经实现map中的大部分函数，除了了 entrySet这个接口 源码声明(AbstractMap)；
    public abstract Set<Entry<K,V>> entrySet();
entrySet 接口返回值类型是一个由Map.Entry组成的set集合,是一个abstract成员方法，这一个唯一需要
我们自己实现的，如果继承AbstractMap，通过实现该方法,返回自己想要的key-value集合，定制特有的Map。   
遍历map的一种方式就是:map.entrySet.iterator()，entrySet我们已经知道是什么了,如果我们想要对Map
里面的元素的Entry的 put,get set等map操作，
我们首先需要知道这些操作来自哪里，同样来自Map这个接口中，属于map的子接口，Map.Entry,该接口定义了
V setValue(V value); V getValue(); K getKey(); 是针对key-value这个entry的基本操作，我们经常
使用的put get 方法都是针对entry的set集合进行的，get(Object object),就是通过map.entrySet.iterator()， 
遍历集合找到相应的entry，put方法在AbstractMap中是不支持的，所以如果想要使用put 需要自己去override AbstractMap
中的put就可以了,通过查看HashMap的源码就可以看到，HashMap override了put get等方法,当然了也必须有一个自己的Map.Entry
不然put 和get，因为put和get操作的对象就是Mao.Entry类,没有自己的Map.Entry是无法保证，put 和get操作,是否会被转义
成为其他的操作。
就可以发现这
### 使用场景
在一些特殊的场景中，需要进行定制化的map，比如只读的map
我们可以继承AbstractMap，然后实现其中的entryset，
如果想要定制get set 函数，可以自己再实现一个Map.Entry
来初始化map，并且override已有的 get set 就可以实现只读的功能。
就像我们经常使用的HashMap就是对AbstractMap的实现，并且定制，我们也可以extends AbstractMap来实现一些简单的操作，
通过这些操作加深对java map容器的理解，再去看Map 使用Map就有更好的效果.
### 

## List
比起Map list 操作起来更简单，但是如果看了List的整个体系结构，就可以发现，其实并不是简单，相对来说更复杂，所以
事情总不是像表面上name简单。首先list肯定是一个接口，源码声明：
public interface List<E> extends Collection<E> ，该接口声明了我们在使用list的所有的操作函数。
再来看看一下  Collection
public interface Collection<E> extends Iterable<E> 
Iterable 就不再说明， 这个就是一个迭代器的接口，使用foreach，遍历list的，这也是为什所有的list和set 都可以
使用迭代器，还有就是上面提到的Mao.entrySet,为何可以用Map.entrySet.iterator()来遍历Map，因为实现了Interator接口。
向上到Iterator就结束啦。
咱们从ArrayList向上看看，其结构是如何的,其source code declare：
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
List已经介绍过了，咱们看AbstractList,的定义
    public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> 
AbstractList继承了AbstractCollection，AbstractCollection 应该实现了Collection，show' time
    public abstract class AbstractCollection<E> implements Collection<E> 
 看来猜测完全正确，那这里产生了一个新的问题，AbstractList本身已经是实现List，那为啥ArrayList还要再实现List呢？
 百度了一下，有人说，这个是作者写的时候写错了，但是因为没有影响 所以也就没有处理，这个咱们也不深究，当然了
 如果你可以很好的解释的，请分享给我哈哈。
 到这里基本上就把ArrayList从上到下的分析了一遍，当然具体的源代码实现是没有的，这里只是分析了一下整体的一个类图
 通过源代码的方式，当然了如果你把类图搞清楚了，把关系理清楚了，再去看代码就会比较方便了，如果想看具体的代码分析
 可以等下一篇博客，
 总结：通过ArrayList咱们发现， 其实可以通过AbstractList来定制属于自己的List，因为通用的大部分代码AbstractList已经实现
 了，但是如果想定制 add，new ， get remove 等操作，就可以override已有的函数就可以实现定制化，甚至是通过继承ArrayList本身。
### 
|List |保持存入顺序 | 是否排序 | 必须操作| 实现方式| 线程安全|
|:----|:----|:----|:----|:------|:------|
|ArrayList | 是 | 否| 无| 数组 | 否|
|LinkedList | 是| 否| 
## Set
set 本身是一个接口
使用自底向上分析法，以HashSet为例子：
```
    public class HashSet<E>
        extends AbstractSet<E>
        implements Set<E>, Cloneable, java.io.Serializable
    public abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E> 
    public interface Set<E> extends Collection<E>
```
对于set由于其特性是保护数据的唯一性，所以需要重写equal方法，不然
会导致无法预料的问题。

## 总结
AbstractList 和 AbstractMap对于添加操作都是属于可选的操作，基本都是不支持的操作，因为她不知道添加数据的类型
所以都是由实现类去实现添加操作，我觉得主要原因是因为无法进行安全检查吧，所以如果你自己继承的AbstractList/Map没有
实现add put等添加函数，但是使用的话，在运行时会出现 :
java.lang.UnsupportedOperationException 
上面的异常，不要吃惊，因为这些操作属于可选操作，为了提高扩展性吧，具体我也还弄明白。

| 名称 |保持存入顺序 | 是否排序 | 必须操作| 实现方式| 线程安全|
|:----|:----|:----|:----|:------|:------|
|HashSet | 否 | 否| hashcode| 未知| 否|
|TreeSet | 否| 是|   comparable| 未知 | 否|
|LinkedHashSet | 是| 否| 无| 未知| 否|
|ArrayList | 是 | 否| 无| 数组 | 否|
|LinkedList | 是 | 否| 无|未知| 否| 
|HashMap | 是 | 否| hashCode|未知| 否| 
|LinkedHashMap | 是 | 否| 无|未知| 否| 
|TreeMap | 是 | 否| comparator| 未知| 否|
|concurrentMap | 是 | 否| comparator|未知| 否| 
