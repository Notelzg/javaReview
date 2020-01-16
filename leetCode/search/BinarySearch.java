package search;

import ListAanTree.utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BinarySearch {

    /**
     * 二分查找优化，夹逼准则，所以遇到重复值的时候，可以向前移动，找到重复值的第一个下标
     * @param arr
     * @param target
     * @return
     */
    public int searchLeft(int[] arr, int target){
        if (null == arr || arr.length == 0)
            return -1;
        int left = 0;
        int right = arr.length - 1;
        int mid;
        while(left < right){
            //落到左 区间
            mid = (left + right ) >>> 1;
            //  注意还有两个元素的时候，一直落到同一个区间，造成死循环从而造成死锁。
           if (arr[mid] < target)
               left = mid + 1;
           else
               right = mid;
        }
       return  (arr[left] == target)  ? left : -1;
    }

    /**
     *二分查找优化，夹逼准则，所以遇到重复值的时候，可以向后移动，找到重复值的最后一个下标
     * @param arr
     * @param target
     * @return
     */
    public int searchRight(int[] arr, int target){
        if (null == arr || arr.length == 0)
            return -1;
        int left = 0;
        int right = arr.length - 1;
        int mid;
        while(left < right){
            // 右区间,
            mid = (left + right + 1) >>> 1;
           //  注意还有两个元素的时候，一直落到同一个区间，造成死循环从而造成死锁。,由于mid落在右区间，所以right = mid +1
           // 可以处理这种情况
            if (arr[mid] > target)
                right = mid - 1;
            else
                left = mid;
        }
        return  (arr[left] == target)  ? left : -1;
    }

    @Test
    public void test(){
        Assertions.assertEquals(-1,searchLeft(utils.str2intArr("1,2,2,3,4,5"), 6));
        Assertions.assertEquals(1, searchLeft(utils.str2intArr("1,2,2,3,4,5"),2));
        Assertions.assertEquals(0, searchLeft(utils.str2intArr("2,2,2,2,2,2"),2));
        Assertions.assertEquals(-1,searchRight(utils.str2intArr("1,2,2,3,4,5"), 6));
        Assertions.assertEquals(2, searchRight(utils.str2intArr("1,2,2,3,4,5"),2));
        Assertions.assertEquals(5, searchRight(utils.str2intArr("2,2,2,2,2,2"),2));
    }
}
