package level_easy;

public class MaximumSubarray {
    public int maxSubArray(int[] nums) {
        if (nums == null)
            return 0;
        int sum = 0;
        int ans = nums[0];
        for (int key : nums){
            if (sum > 0){
                sum += key;
            }else {
               sum = key;
            }
            ans = Math.max(sum, ans);
        }
        return ans;
    }

}
