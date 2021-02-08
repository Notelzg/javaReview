# tomcat clock 一次 jstack 分析

##  jstack 使用
stack 是 JDK 自带的工具，用于 dump 指定进程 ID(PID)的 JVM 的线程堆栈信息。
### 打印堆栈信息到标准输出
jstack PID
### 打印堆栈信息到标准输出，会打印关于锁的信息
jstack -l PID
### 强制打印堆栈信息到标准输出，如果使用 jstack PID 没有响应的情况下(此时 JVM 进程可能挂起)，加 -F 参数
jstack -F PID

## 日志
...............
引用：https://blog.csdn.net/lmb55/article/details/79349680
### thread 状态：
```
1、NEW,未启动的。不会出现在Dump中。
2、RUNNABLE,在虚拟机内执行的。
3、BLOCKED,受阻塞并等待监视器锁。
4、WATING,无限期等待另一个线程执行特定操作。
5、TIMED_WATING,有时限的等待另一个线程的特定操作。
6、TERMINATED,已退出的。
```
### 锁的状态
```
1, 
```
### 定位死锁的地方
正常都是有很多的Blocked，有1个WAITING，waiting一般都是OBject.wait导致的
blocked一般都是由于 synchronize锁导致的。通过blocked的线程，上下文可以到
具体在等到某个锁地址，通过该地址全局搜索，找到已经已经 clocked 该地址的线程
这里就是导致死锁的线程，铜鼓观察上下文找问题， 看下面的第第二行，第四行
```
# 只截取部分
1,http-mloz-29999-exec-250"#328 dobron prio-5 os_prio- tid-@-00007f8690005000 nid-@x236cc mditing for onl tor entry [0x00007F3az
2,java.lang.Threod.State: BLOCKED (on dbject monitor)
3,at com.google.comtnsooliers SHonoizingSuoller.get(Supplters.Jova:130)
4,－woiting to 080000000d497f5d8(acon.googla.cormon.bose.SuppliersDnoi ringSupplier)

// waing 
11,http-mloz-29999-exec-251"#328 dobron prio-5 os_prio- tid-@-00007f8690005001 nid-@x236cc mditing for onl tor entry [0x00007F3az
12,java.lang.Threod.State: WAITTING (on dbject monitor)
13,at java.lang.Object.wait(Native Method)
14,at java.lang.Object.wait(Object.java 502)
15,at java.lang.UNIXProcess.waitFor(YNIXProcess.java 395)
16,－locked 080000000d497f5d8(acon.googla.cormon.bose.SuppliersDnoi ringSupplier)
   at ..... 
   at ..... 

```

