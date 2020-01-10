package ListAanTree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

/**
 * 一个有序的数组，求该数组中最长等差数列的长度。
 * 输入：
 * [1, 1, 2,3, 5,6, 8,9,10 11, 14]
 * 输出：
 * 5
 * 等差数列 ： 2,5,8,11,14
 * 思路1
 * 使用穷举法，计算一个元素 和其后所有元素的差值，根据差值找等差数列
 * 从第一个元素到倒数第二个元素，都需要执行这个过程，找到最大的。
 */
public class FindMaxArithmeticSequence {
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
                       pre = index;
                       index++;
                       countLen++;
                   }else if (arr[index] < arr[pre] + diff){
                      index++;
                   }else {
                       break;
                   }
               }
               max = Math.max(countLen, max);
            }
        }
        return max;
    }
    @Test
    public void test(){
        Assertions.assertEquals(5, findMaxArithmeticSequence(utils.str2intArr("1, 1, 2,3, 5,6, 8,9,10 11, 14")));
    }
}
