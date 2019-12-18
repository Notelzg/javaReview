package ArrayAndSorted;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 */
public class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums.length < 3)
            return ans;
        // 排序
        Arrays.sort(nums);
        //特殊情况排除外,全部0
        if (nums[0] == 0 &&   nums[nums.length - 1] == 0) {
            ans.add(Arrays.asList(0, 0, 0));
            return ans;
        }
        // 都是正数 或者负数
        if (nums[0] > 0 || nums[nums.length - 1] < 0)
            return ans;
        int head , tail, sum;
        //肯定需要一个负数,遍历所有的负数
        for (int i = 0; nums[i] < 0 && i < nums.length - 2; i++) {
            head = i+1;
            tail = nums.length - 1;
            while (head < tail) {
                sum = nums[head] + nums[tail] + nums[i];
                if (0 == sum) {
                    ans.add(Arrays.asList(nums[i], nums[head], nums[tail]));
                   //去重
                    while (i < nums.length-1 && nums[i] == nums[1+i])
                        i++;
                    while (head < tail && nums[tail] == nums[tail -1])
                        tail--;
                    while (head < tail && nums[head] == nums[head + 1])
                        head++;
                    head++;
                    tail--;
                } else if (sum > 0) {
                    //尝试更小的整数
                    tail--;
                } else {
                    head++;
                    //尝试更大的负数
                }
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
        threeSum(IntStream.of(-4,-2,-2,-2,0,1,2,2,2,3,3,4,4,6,6).toArray())
                .forEach(key->{key.forEach(keyC->System.out.print(keyC+ " ")); System.out.println();});
        System.out.println();
        threeSum(IntStream.of(-2,-2, 2, -1, 0, 0, 0, 0,1).toArray())
                .forEach(key->{key.forEach(keyC->System.out.print(keyC+ " ")); System.out.println();});
        System.out.println();
        threeSum(IntStream.of(0, 0,0, 0, 0, 0).toArray())
                .forEach(key->{key.forEach(keyC->System.out.print(keyC+ " ")); System.out.println();});
        System.out.println();
        threeSum(IntStream.of(-1, 0, 1, 2, -1, -4).toArray())
                .forEach(key->{key.forEach(keyC->System.out.print(keyC+ " ")); System.out.println();});

    }
}
