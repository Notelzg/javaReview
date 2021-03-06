# enum 枚举类
## enum
```
 enum Test { red, green, blue}
```
 枚举类型是final类型，不可以被修改继承，其值从默认0开始计算
 引用enum，可以通过 static import引用
 这样可以直接使用 red， green ，不用带 Test前缀。
 ## enumSet
  枚举的Set集合，其元素必须来自enum类型，可以通过泛型class参数
  来处理不同类型的 Enum集合，实现更好的扩展性。
 ## enumMap
 enumMap 其key必须来自一个enum，由于enum的特殊性，所以hashMap内部
 可以使用数组来存储，提高其性能，适合多路分发。
 ## 练习
由于enum类，可以定义Abstract方法，而其enum itme 可以实现该abstract
达到一个多态的目的，加上enumSet拒绝重复的重复，所以用enum类，或者把enum作为
内部类，进行一些关于命令行的操作，是非常便捷的， 这是我目前想到的一些使用场景
就像书里面讲的，这个和表驱动代码很像，一个标识，代表一个方法，如果你熟悉驱动编程
或者mbr这类东西，你应该可以明白的，具体的实用场景，就看情况而定了
## 总结
   我们学习java语言，或者其他语言的目的是什么,语言只是工具，如果做到语言无关性呢，
比如学习的枚举类型，也许别的语言根本就没有枚举类型呢。都说理论联系实际，理论就一套
但是实践有很多种途径，并且每个人的可能还不一样，每种语言可能也不一样，但是我觉得理
论是一种类似真理的东西 具有比较长的时效性的, 而且理论存在的好处是，不同的语言应该
都会去实现这种理论(好的，正确的，可以提高产出效率的)， 使这种理论的实现作为该语言的
一个特性，在java里面就是一个类，在c++里面可能是别的，不同的语言实现方式不一样，但是
其理论应该是相通的，就像操作系统理论，虽然有很多的操作系统，但是其理论一定是按照计算机
操作系统理论的，只是其在实现的过程中，各有各自的特点，但是该有的功能肯定有，这些功能
就是理论的实现，我们不仅应该把这些功能学会，用好，还要弄明白这些功能背后的理论支撑
不仅是学习一个类 功能怎么使用，这样你就只是会这一个，如果你搞懂了背后的理论，那你就是
学会了一类，这就是每一个牛逼功能背后都站着一个牛逼的理论。学习学习，如何处类旁通，如果
举一反三，都是因为他们搞明白了背后的那一套，所以才能以不变应万变。
## 枚举类背后站着的理论
 多路分发，其实多路分发这个理论，在驱动层特别多，因为驱动层做的工作就是多路分发的工作
 通过分发给不同的驱动程序，支撑操作系统对硬件的调用，完成系统相应的功能，比如说打印机驱动
 程序，我们可以安装不同的驱动程序，支撑不同的打印机设备，而操作系统不用做任何的修改，不需要
 提前知道，驱动程序的类型，只要你的驱动程序实现规定的功能就行，这个就是通过多路分发实现的。
 其原理是我不同提前知道你的类型，你只要注册进来，用你的时候会自动调用你提供的功能。核心
 思想就是参数类型可以使未知的，通过java的重载，windows就是注册表，或者map、set这种数据结构
 来实现注册的功能，都是扩展性非常强的。参数可以未知，通过重载不同的参数类型，找到你的类型
 然后进行相应的处理，像java中表达式的处理都是这样大的，1+2+3，这种最简单的处理都是使用
 这种思想进行的，因为这是一个字符串，首先一个祖先就是object ，通过重载+ - * /不同的数据
 类型，int double  long string 等，来实现的。在具体的使用的时候，推断数据类型，或者解析
 公式的时候，推断出类型。 
 ## 适用场景
 可以使用枚举，来处理状态机，状态的改变，或者处理链的处理，通过values函数，返回所有的元素进行
 处理，或者是不同状态的处理，都是比较方便，合适的选择。
 notice
 优雅与清晰很重要，正是他们区别了成功的解决方案与失败的解决方案，而失败的解决方案就是因为其他人
 无法理解它。 ---java编程思想 第四版 619页，枚举类型总结中摘抄的。
