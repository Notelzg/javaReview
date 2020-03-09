# 滴滴面试
## 2020.1.07, 滴滴二面技术
### 第一面
主要是基础问题
#### linux命令
一个email文件
#### 类加载过程
String str = new String("abc");
创建了几个对象
```
 String str = new String("abc");
 String str2 = new String("abc");
 System.out.println(str2 == str); //false
 String str3 = "abc";
 String str4 = "abc";
 System.out.println(str3 == str4); // true
```
以上代码字节码文件如下
4, 14 36 39, idc #3, #3是字符串常量池中的“abc”，
“abc"本身是一个String类型的是一个对象
0行 new关键字新建一个对象，对象指向 已有的“abc” 对象，所以如果加上”abc"
就是新建两个对象，否则就是1个对象。
jvm指令集 https://juejin.im/entry/588085221b69e60059035f0a
```

Constant pool:
   #1 = Methodref          #8.#20         // java/lang/Object."<init>":()V
   #2 = Class              #21            // java/lang/String
   #3 = String             #22            // abc
   #22 = Utf8               abc
//只截取了一分部，常量池中的
Compiled from "ObjectCache.java"
public class test.ObjectCache {
  public test.ObjectCache();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: new           #2                  // class java/lang/String
       3: dup
       4: ldc           #3                  // String abc
       6: invokespecial #4                  // Method java/lang/String."<init>":(Ljava/lang/String;)V
       9: astore_1
      10: new           #2                  // class java/lang/String
      13: dup
      14: ldc           #3                  // String abc
      16: invokespecial #4                  // Method java/lang/String."<init>":(Ljava/lang/String;)V
      19: astore_2
      20: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
      23: aload_2
      24: aload_1
      25: if_acmpne     32
      28: iconst_1
      29: goto          33
      32: iconst_0
      33: invokevirtual #6                  // Method java/io/PrintStream.println:(Z)V
      36: ldc           #3                  // String abc
      38: astore_3
      39: ldc           #3                  // String abc
      41: astore        4
      43: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
      46: aload_3
      47: aload         4
      49: if_acmpne     56
      52: iconst_1
      53: goto          57
      56: iconst_0
      57: invokevirtual #6                  // Method java/io/PrintStream.println:(Z)V
      60: return
}

```
### el表达式缓存导致outMemory如何进行优化
查看el表达式是如何进行缓存的，把本地缓存换成缓存中间件
如何用redis/memCache替换el表达式的缓存

#### 编程题
把单链表 每两个节点进行反转，如果节点个数是奇数个，最后一个不反转.
例子1，
输入：
1-->2-->3-->4
输出
2-->1-->4--->3
例子2
输入：
1-->2-->3
输出
2-->1-->3
```
 public ListNode reverse(ListNode root){
        if (root == null || root.next == null)
            return root;
        ListNode pre = root;
        ListNode next = root.next;
        ListNode head = new ListNode(0);
        //记录一个前节点，使反转之后的链表和已经反转的链表链接到一块
        ListNode prePre = head;
        while (next != null){
            pre.next = next.next;
            next.next = pre;
            prePre.next = next;
            prePre = pre;
            if (pre.next == null)
                break;
            pre = pre.next;
            next = pre.next;
        }
        return head.next;
    }
```
### 第二面
#### 自己做过的项目如何进行优化
数据同步对比一条条从数据库取太慢了，如何优化。
使用缓存技术，使用什么缓存redis/memcache，如何进行优化
#### 编程题
给你一个20G文件，文件内容是每行是一个 email地址， email地址是无序的，统计出email的top10.
内存限制: 只允许使用1G的内存.
解法思路
归并排序的思想，可以在内存不够的时候使用硬盘来完成排序。
先读取1G内容email，然后使用快速排序进行排序，得到一个有序的email，然后放入一个temp1缓存文件
再去取1G内容email，排序之后得到一个有序的数组，然后对比把小的email写入temp2文件，写完就得到
了一个2G有序的temp2，持续这个过程，直到得到一个有序的20G的email文件。
文件有序之后，按行读取碰到本次emal和上一个email不一样则表示这个email已经统计完成，把统计
结果插入到长度为10的top数组中，如果数组未满则使用插入排序放入，如果数组已满则使用插入排序
找到插入点，但是把插入点前面的元素都向前移动一位，然后把数据放入插入点。
同时把email数组进行同样的处理，由于处理过程一样所以同时进行处理。
代码实现，假如文件里面有100个Email，但是你只能使用一个数组长度为10的
数组，取出top10。和处理20G思路一样，代码也可以复用，只是把100行换成
1G应该是多少行呢，其实也不好算，咱们可以使用byteArrya读取1G，因为有行分隔符
所以可以进行分隔符的切割成数组或者list，然后使用Arrays.sort(array)/Collection.sort(list)
排序，接着进行归并，写入临时文件。
来进行排序，排序结果可以存入找到top10
```

```
