# , 记录书中自己觉得经典的语句以及自己的一些理解。
Thing in java edit4 
# 1, P28
原话： 将对象作为服务提供者看待是一件伟大的简化工具。
自己的感悟：发现java sprig 3层框架 entiyt->mapper->xml->service->serviceImp
dao层就不说了，一直都是这样的，虽然这样确实少写了很多代码，但是dao层目前来说
# 2 protected
使用场景：子类继承父类的时候，类似于c++的 friend
protected关键字可以保证该域中的变量、方法不被外部类调用除了导出类（继承）
但是protected 并不不是被用来保护成员变量的，而是对外提供方法的。为什么不在
变量声明的时候使用protected是因为，这样会导致无法控制访问该变量的入口，导致
该变量无法保证数据的同步，使用魔法方法来访问，被声明为private的变量，可以保证
访问该变量的入口是唯一的，保证数据的同步。
总结：所有的成员变量就应该声明为private。访问变量通过方法访问，而不是直接访问
该变量，除了在该类的内部,保证数据的安全性,养成良好的编程习惯。
