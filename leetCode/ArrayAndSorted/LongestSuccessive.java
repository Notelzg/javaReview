package ArrayAndSorted;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

/**
 * 给定一个未排序的整数数组，找出最长连续序列的长度。
 * <p>
 * 要求算法的时间复杂度为 O(n)。
 * <p>
 * 示例:
 * <p>
 * 输入: [100, 4, 200, 1, 3, 2]
 * 输出: 4
 * 解释: 最长连续序列是 [1, 2, 3, 4]。它的长度为 4。
 * 思路1， ,时间复杂度 o(n * logN)
 * 先快速排序，然后这个题就变成了，找出一个连续递增的最长子序列题目了
 * 思路2，
 * 使用hashMap的思想，把数据初始化到hash中,然后遍历hash就可以了，
 * 思路是一样的，因为放到hash中之后，其实每次可以取出 num[i]+1的值
 * 所以也是排序的思想，但是需要注意一个点就是，使用set进行去重
 */
public class LongestSuccessive {
    public int longestConsecutive(int[] nums) {
        int length = nums.length;
        if (length < 2) return length;
        Arrays.sort(nums);
        int maxLen = 0;
        int len = 1;
        int pre = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1])
                continue;
            if (nums[i] == pre + 1) {
                len++;
                maxLen = Math.max(len, maxLen);
//                //去重
//                while (i < nums.length -1 && nums[i] == nums[i + 1])
//                    i++;
            } else {
                len = 1;
            }
            pre = nums[i];
        }
        return maxLen;
    }

    public int longestConsecutiveHash(int[] nums) {
        int len = nums.length;
        if (len < 2)
            return len;
        int maxLen = 0;
        int curLne = 1;
        int cur = 0;
        HashSet<Integer> set = new HashSet();
        for (int key : nums)
            set.add(key);
        for (Integer key : set) {
            /**
             因为是非排序的，所以要向前和向后找，才能找到所有的元素 ,所以需要两个while分别向前和向后查找然后加起来就是结果
             但是如果我们可以找到非排序数组的第一个元素就 可以沿着一个方向进行查找不需要进行递归，第一个元素一定是小于key的
             所以向前找key-1，知道key-1不存在则证明，key是第一个元素. 因为我们使用的是for循环会遍历所有元素，所以不需要进行
             单独key-1循环处理，
             **/
            // 不包含证明是子序列的第一个元素，最小值
            if (!set.contains(key-1)){
                cur = key + 1;
                curLne = 1;
                while (set.contains(cur)) {
                    cur++;
                    curLne++;
                }
                maxLen = Math.max(curLne, maxLen);
            }
        }
        return maxLen;
    }

    @Test
    public void test() {
        Assertions.assertEquals(4, longestConsecutiveHash(new int[]{100, 4, 200, 1, 1, 3, 2}));
        Assertions.assertEquals(1, longestConsecutiveHash(new int[]{2}));
        Assertions.assertEquals(4, longestConsecutive(new int[]{100, 4, 200, 1, 1, 3, 2}));
        Assertions.assertEquals(1, longestConsecutive(new int[]{2}));
    }
}
