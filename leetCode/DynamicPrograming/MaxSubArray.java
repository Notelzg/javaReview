package DynamicPrograming;

import ListAanTree.utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class MaxSubArray {
    /**
     * 分治思想
     * 最大子数组存在3种情况， left --> mid， mid +1 -->right, i mid j
     * 找出其中的
     * 时间复杂度是 2(T/2) + O(n), 整个递归过程和归并排序一样，O(n)相当于合并所以是O(n lgn)
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        return findMaxSub(nums, 0, nums.length-1);
    }
    private int findMaxSub(int[] nums, int left, int right){
        if (left == right)
            return nums[left];
        int mid = (left + right) /2 ;
        int l = findMaxSub(nums, left, mid);
        int r = findMaxSub(nums, mid + 1, right);
        int cross = findCrossMid(nums, left, right);
        return Math.max(cross, Math.max(l, r));
    }
    private int findCrossMid(int[] nums, int left, int right){
        int mid = (left + right) /2 ;
        int maxLeft;
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = mid; i >= left; i--){
            sum += nums[i];
            if (sum > leftSum){
                leftSum = sum;
                maxLeft = i;
            }
        }
        sum = 0;
        int rightSum = Integer.MIN_VALUE;
        int maxRight ;
        for (int i = mid + 1; i <= right; i++){
            sum += nums[i];
            if (sum > rightSum){
                rightSum = sum;
                maxLeft = i;
            }
        }
        return rightSum+leftSum;
    }
    public int maxSubArray1(int[] nums) {
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
