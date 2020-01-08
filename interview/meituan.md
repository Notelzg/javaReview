# 美团面试
## 2020/01/06 一面
### 编程题
一个有序的数组，求该数组中最长等差数列的长度。
输入：
[1, 1, 2,3, 5,6, 8,9,10 11, 14]
输出：
5
等差数列 ： 2,5,8,11,14
思路1
使用穷举法，计算一个元素 和其后所有元素的差值，根据差值找等差数列
从第一个元素到倒数第二个元素，都需要执行这个过程，找到最大的。
```
  public int findMaxArithmeticSequence(int[] arr) {
        if (arr == null || arr.length < 3)
            return arr.length;
        int len = arr.length;
        int max = 0;
        int diff;
        int pre;
        int countLen;
        int index;
        for (int i = 0; i < len -1; i++){
            for (int j = i +1; j < len; j++){
               diff = arr[j]  - arr[i];
               index = j + 1;
               pre = j;
               countLen = 2;
               while (index < len){
                   if (arr[index] == arr[pre] + diff){
                        //非连续，所以需要记录上一个值的下标
                       pre = index;
                       index++;
                       countLen++;
                   }else if (arr[index] < arr[pre] + diff){
                      //小于的时候，继续去下一个
                      index++;
                   }else {
                        //大于的时候，由于是有序的数组，所以此时就是当前元素，等差值得最长等差数列。
                       break;
                   }
               }
               max = Math.max(countLen, max);
            }
        }
        return max;
    }
```

### 对象在堆中占的内存大小
对象头 markwork + 类引用
开启指针压碎 指针变为从8压缩到4字节
integer对象内存多大

### 数据库查询过程
### pareNew回收机制
### redis数据结构
### redis list 什么时候可以转为 kafka 的队列替换
### redis数据结构底层实现
### 数据库隔离机制
### 如何进行sql优化 使用索引
### 栈参数传递
