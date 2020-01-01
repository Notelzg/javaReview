package DynamicPrograming;

import ListAanTree.utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
 * <p>
 * 示例:
 * <p>
 * 输入: [10,9,2,5,3,7,101,18]
 * 输出: 4
 * 解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
 * 说明:
 * <p>
 * 可能会有多种最长上升子序列的组合，你只需要输出对应的长度即可。
 * 你算法的时间复杂度应该为 O(n2) 。
 * 进阶: 你能将算法的时间复杂度降低到 O(n log n) 吗?
 * 思路1，暴力解法 ，
 * 找出所有的递增序列，然后输出最大的,使用递归法
 */
public class MaxIncreaseSequence {
    /**
     * 因为普通的动态规划时间复杂度 O(n^2)
     * 如果想减低 只能通过二分查找的想法，降低查找的速度,直接返回当前元素，最大的子序列长度
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        if (null == nums || nums.length == 0)
            return 0;
        int len = nums.length;
        if (len == 1)
            return 1;
        int[] dp = new int[len];
        dp[0] = nums[0];
        int end = 0;
        for (int i = 1; i < len; i++){
            if (nums[i] > dp[end]){
                dp[++end] = nums[i];
            }else {
                int left = 0;
                int right  = end;
                int mid;
                while (left < right){
                    mid = (left + right) >>> 1;
                    if (nums[i] > dp[mid]){
                        left = mid + 1;
                    }else {
                        right = mid;
                    }
                }
                dp[left] = nums[i];
            }
        }
        return end + 1;
    }
    /**
     * 动态规划,dp[i] = dp[0...i-1] 中小于dp[i] + 1,
     * dp[i] = 当前元素的值，最长子序列
     * 时间复杂度 O(n^2), 空间复杂度O(n)
     * @param nums
     * @return
     */
    public int lengthOfLISDp1(int[] nums) {
        if (null == nums || nums.length == 0)
            return 0;
        int len = nums.length;
        if (len == 1)
            return 1;
        int[] dp = new int[len];
        Arrays.fill(dp, 1);
        int max = 0;
        for (int i = 1; i < len; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j])
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }

    /**
     * 递归法
     *
     * @param nums
     * @return
     */
    public int lengthOfLISRecursive(int[] nums) {
        if (null == nums || nums.length == 0)
            return 0;
        int len = nums.length;
        if (len == 1)
            return 1;
        return maxLength(nums, Integer.MIN_VALUE, 0);
    }

    private int maxLength(int[] nums, int prev, int cur) {
        if (cur == nums.length)
            return 0;
        int len1 = 0, len2;
        if (nums[cur] > prev)
            len1 = 1 + maxLength(nums, nums[cur], cur + 1);
        len2 = maxLength(nums, prev, cur + 1);
        return Math.max(len1, len2);
    }

    @Test
    public void test() {
        Assertions.assertEquals(3, lengthOfLIS(utils.str2intArr("[10,9,2,5,3,4]")));
        Assertions.assertEquals(4, lengthOfLIS(utils.str2intArr("10,9,2,5,3,7,101,18")));
    }

}
