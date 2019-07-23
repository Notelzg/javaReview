# 并发编程
并发可以说是计算机操作系统发展的的重要里程碑，随着分时复用理论的提出，计算机的发展从单一
任务处理器变成了多任务处理器（虚拟），随着多核cpu的出现，计算机在物理上也实现了多任务处理，但是
就像内存有多大程序就有多大意义，计算机的存在的目的就是为了提高运算速度，为了更进一步提升
进程速度，充分的利用计算机资源，线程作为一个轻量级的进程出现了，首先线程依赖于进程，和其他
线程共享进程的内存空间和资源，同时完成进程内部的任务，这就是线程的并发，多个进程同时运行的
叫做多任务，进程之间是相互独立的所以互不影响，线程共享进程的所有资源，所以通过并发提高运算
速度的同时，同时面临着对共享资源访问的同步问题，而并发编程主要解决的就是同步数据问题.
  JAVA通过Thread类定义了线程，通过实现Runnable接口，继承Thread就可以实现进程，当然内部
匿名方法，变量都是可以的.
## 多线程
## 同步机制
### synchronize
自动刷新内存中的数据
### lock
### Atomic
### 互斥量
通过原子性和可见性实现
## 线程协作
通过阻塞实现
### wait notify
线程的声明周期
### ArrayBlockingQueue
### LinkedBlockingQueue
### CountDownLatch
### CyclicBarrier
### DelayQueue
### PriorityBlockingQueue
### Semaphore
### Exchanger
## 线程安全容器
## 阻塞容器
## 免锁容器
### CopyOnWriteArrayList
### ConcurrentHashMap
### ConcurrentLinkedQueue
## 乐观加锁
### Atomic.compareAndSet
### ReadWriteLock
## 活动对象

