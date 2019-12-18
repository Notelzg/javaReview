package ArrayAndSorted;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 给定一个未经排序的整数数组，找到最长且连续的的递增序列。
 */
public class FindLengthOfLCIS {

    public int findLengthOfLCIS(int[] nums) {
        if (null == nums || 0 == nums.length)
            return 0;
        if (nums.length == 1)
            return 1;
        int  maxLen = 0;
        int  len= 0;
        int pre = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++){
            if (nums[i] > pre) {
               pre = nums[i];
               len++;
               maxLen = Math.max(maxLen, len);
            }else {
                len = 1;
                pre = nums[i];
            }

        }
        return maxLen;
    }
    @Test
    public void test(){
        Assertions.assertEquals(3, findLengthOfLCIS(new int[]{1,3,5,4,7}));
        Assertions.assertEquals(1, findLengthOfLCIS(new int[]{2,2,2,2,2}));
    }
}
