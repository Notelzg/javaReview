# 树
关键字 ： 二叉树、先序、中序、后序、哈夫曼编码
前面说了顺序存储结构，现在说下非顺序结构树的由来。树作为一种从远古到现代的植物，在人类进化的
过程中担任了很多角色，衣、食、住、用、行基本都离不开树，当然了马车现在不用了，但是现在的轮胎
还是用什么胶做的哈哈。当然了在计算机中也不离开树，比如文件系统就是树结构，再比如多级菜单也可以
也是树形结构。
##  定义
树是一种非线性的数据结构，由唯一的根节点和若干颗互不相交的子树组成的。
## 二叉树
二叉树每个节点最多只能有两颗子树。
遍历方式存在三种，preOrder 先序、inOrder 中序、postOrder 后序，遍历节点的顺序不同
```
 递归算法，如下
order (BTNode *p){
  if (p != null){
     //vist(p); 先序
     order(p->leftChild);
     //vist(p); 中序 
     order(p->rightChild);
     //vist(p); 后序
   }
 }
 //非递归算法，需要使用栈来解决，用栈存储节点，根节点入栈，出现一个元素，访问，然后左右子树不为空，则入栈，
 //直到栈空
 // 层次遍历需要使用队列，和使用栈一样的套路，直接一层层遍历，有先进先出特性使用队列来完成，其过程和上面一样。
```
特点是：叶子节点等于双分支节点+1
## 哈夫曼编码
权值最少生成树，，每次合并权值最小的节点，构造而成。因为其编码不存在二义性，而且权值越大路径越短，所以
适合用来做编码，比如咱们使用的ascii、gbk、utf8等，相同的原理。
## 使用场景
树作为一种复杂的数据结构，其使用场景是受限的，只有在特定的场景下才需要使用，比如无限极分类这种结构用
树来存储是最合适的，就像文件系统一样，谁都不知道自己电脑上会有多少文件夹、文件，但是文件系统都可以支持
不管你怎么用，是不是很厉害哈哈，这就是数据结构甚至数据库设计存在的意义了，不管你是什么数据或者操作我都
可以进行承载，只要设计的好，就可以承载任意的数据和支持任意的操作，当然前提是在一定的业务场景下面。
数据结构和数据库的设计都是同样的道理，你算法好不好首先就是由你的数据结构决定了，你的设计的设计扩展和健壮性
基本是由你设计的数据库决定的。
## 总结
其实树没有什么总结，在各种容器或者算法的底层，大量的使用，或者算法中用的比较多，但是其使用确实是特定场合
下面的，如果是无限级结构，就可以考虑树。别的话树作为复杂数据结构的支撑，不理解的二叉树，很难去理解N叉树
而二叉树的真实使用场景，☝应该是比较少的,这个我也没有研究过。