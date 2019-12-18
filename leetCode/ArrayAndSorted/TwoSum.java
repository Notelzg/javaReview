package ArrayAndSorted;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsKt;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 *sh 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class TwoSum {
    /**
     * 两个数的和，差
     * 通过头 , 尾 双指针， 来遍历数组，
     * 场景： 只使用一次数据的场景
     * time analysis  O(nlogn) , 因为要对nums排序
     * space analysis  O(1)
     * @param nums 是一个有序的数组
     * @param target
     * @return
     */
    public int[] twoSumTwoPoint(int[] nums, int target) {
       if (nums.length < 2)
            return new int[]{};
       int head = 0;
       int tail = nums.length - 1;
       int sum;
       while (head < tail){
           sum = nums[head] + nums[tail];
           if (sum == target)
               return new int[]{head, tail};
           else if(sum > target)
               tail--;
           else
               head++;
       }
       return new int[]{};
    }

    /**
     * 用哈希记录已经访问过的值，适合无序的，有序的也可以
     * 适合场景： 建立hash表之后，第二次查询时间复杂度是 O(1), 属于以空间换时间, 所以适合经常使用的数据
     * time analysis  O(n)
     * space analysis  O(n)
     * @param nums 有序 无效都可以
     * @param target
     * @return
     */
    public int[] twoSumHash(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        int sub;
        for (int i = 0; i < nums.length; i++){
            sub = target - nums[i] ;
            if (map.containsKey(sub)){
                return new int[]{map.get(sub), i};
            }else {
                map.put(nums[i], i);
            }
        }
        return new int[]{};
    }

    /**
     * 给定 nums = [2, 7, 11, 15], target = 9
     *
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/two-sum
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    @Test
    public void test(){
        Assertions.assertArrayEquals(IntStream.of(0 , 1).toArray(), twoSumHash(IntStream.of(2, 7, 11, 15).toArray(), 9));
        Assertions.assertArrayEquals(IntStream.of(0 , 1).toArray(), twoSumTwoPoint(IntStream.of(2, 7, 11, 15).toArray(), 9));
    }
}
