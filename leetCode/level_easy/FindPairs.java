package level_easy;

import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;
import java.util.*;

/**
 * 给定一个整数数组和一个整数 k, 你需要在数组里找到不同的 k-diff 数对。这里将 k-diff 数对定义为一个整数对 (i, j), 其中 i 和 j 都是数组中的数字，且两数之差的绝对值是 k.
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/k-diff-pairs-in-an-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 输入: [3, 1, 4, 1, 5], k = 2
 * 输出: 2
 * 解释: 数组中有两个 2-diff 数对, (1, 3) 和 (3, 5)。
 * 尽管数组中有两个1，但我们只应返回不同的数对的数量。
 */
public class FindPairs {
    public static  int findPairs(int[] nums, int k) {
        Objects.requireNonNull(nums);
        Set<List<Integer>> set = new HashSet<>();
        if (nums.length < 2)
            return 0;
        int diff;
        for (int i = nums.length - 1; i > 0; i--)
            for (int j = 0; j < i; j++){
                diff = (nums[i] - nums[j]);
                diff = (diff < 0) ? -diff : diff;
                if (diff == k){
                    if (nums[i] < nums[j]) {
                        set.add(Arrays.asList(nums[i], nums[j]));
                    }
                    else {
                        set.add(Arrays.asList(nums[j], nums[i]));
                    }
                }
            }
        return set.size();
    }

    public static  int findPairs1(int[] nums, int k) {
        Objects.requireNonNull(nums);
        if (nums.length < 2 || k < 0)
            return 0;
        Arrays.sort(nums);
        int count = 0;
        int pre = Integer.MAX_VALUE;
        int start = 0;
        for (int i = 1; i < nums.length; i++){
            if (pre == nums[start] || nums[i] - nums[start] > k){
                start++;
                if (start != i) {
                    i--;
                }
            }else if (nums[i] - nums[start] ==k){
                pre = nums[start];
                count++;
                start++;
            }
        }
        return count;
    }
    @Test
    public void test(){
        System.out.println(FindPairs.findPairs1(new int[]{1,2,3,4,5}, -1));
        System.out.println(FindPairs.findPairs1(new int[]{1,2,3,4,5}, 1));
        System.out.println(FindPairs.findPairs1(new int[]{6,7,3,6,4,6,3,5,6,9}, 4));
    }
}
