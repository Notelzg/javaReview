# java 编程规则
## 1, final static 
使用 static 和 final 修饰的域，属于编译期间产生的数据，通常起命名规范是
全部用大写表示，多个单词就用下划线连接起来。
## 2, final initial
必须在域的定义处赋值，或者在构造函数中给未初始化的final域初始化. 这是为了防止final域被重写。
final域不能被重写，代表着final域不能被继承。
## 3 , extends
java类，通过两种方式加载，一个是创建对象，一个是调用静态域，main方法也是一个静态域,加载的时候
优先加载基类，不管你是否调用。继承是可以快递的获取基类的接口，但是不够灵活。但是组合和代理更灵活一点。
## 4, dev
程序的开发是一个增量的过程，犹如人的学习一样，这一点很重要。程序开发依赖于经验，你可以
尽自己的能力去分析所有的情况，但是当你开始去做的时候，你仍然无法知道所有的答案。☝应该将项目
看成是一个养成游戏，而不是割草游戏。
