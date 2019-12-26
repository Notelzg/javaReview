package DynamicPrograming;

import ListAanTree.utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class MaxSubArray {
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++){
            if (sum > 0){
                sum = sum +nums[i];
            }else {
                sum = nums[i];
            }
            max = Math.max(max, sum);
        }
        return max;
    }
    @Test
    public void  test(){
        Assertions.assertEquals(6, maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
        Assertions.assertEquals(6, maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
    }
}
