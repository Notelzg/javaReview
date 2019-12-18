package ArrayAndSorted;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 */
public class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums.length < 3)
           throw new RuntimeException("数组长度不够");
        // 排序
        Arrays.sort(nums);
        //特殊情况排除
        if (nums[0] > 0 || nums[nums.length - 1] < 0)
            return ans;
        // 查找0的下标
        boolean zeroFlag = false;
        int zeroIndex = 0;
        while (zeroIndex < nums.length && nums[zeroIndex++] != 0);
        if (nums[--zeroIndex] == 0)
           zeroFlag = true;
        /**
         * 使用头尾双指针分别指向正数和负数， count = 2， 初始值是2
         * head = 0， tail = nums.length - 1
         * List<Integer> item;
         * item.add(nums[heal[);
         * item.add(nums[tail[);
         * int sum = nums[head] + nums[tail]
         * 如果 head tail ,如果 sum < 0 ,如果count < 3 则tail-- , cunt++, sum+=nums[tail], item.add(nums[tail]);
         *                      如果 count ==3，则说明负数太大了，需要 重新计算sum 减去第一次添加的正负数，count--
         *                      sum > 0  head++,如果count < 3 则head++ , cunt++, sum+=nums[tail], item.add(nums[tail]);
         *          *                      如果 count ==3，则说明正数太大了，重新计算sum 减去第一次添加的整数， count--
         *                      sum == 0 , 如果 count == 2存在0，则返回 nums[head] 0 mums[tail] , 如果count ==3，则返回 item
          */
        int head = 0, tail = nums.length - 1, sum = nums[head] + nums[tail];
        int n1 = nums[head], n3 = nums[tail], n2 = 0;
        int[] three = new int[3];
        int count = 0;
        while (head <= tail){
           if (sum < 0){
                if (count < 3){
                    sum += nums[--tail];
                    count++;
                }else {
                    sum -= n1;
                }
           } else if (sum > 0) {
                if (count < 3){
                    sum += nums[++head];

                }
           }else {

           }
        }
        return ans;
    }
    /**
     * 例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
     *
     * 满足要求的三元组集合为：
     * [
     *   [-1, 0, 1],
     *   [-1, -1, 2]
     * ]
     */
    @Test
    public void test(){
        System.out.println( String.join(".", "a"));
    }
}
