package ArrayAndSorted;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 * 在数组排序之后，第k个最大元素的下标是 index = nums.length - k
 * 根据快速排序的思想，每一次排序可以唯一确定一个元素的最终位置，通过两个位置的对比我们可以知道
 * 继续进行哪边的排序，把快速排序的时间复杂度从 o(n * logN) 降低一倍也就是 O(n/2 * logN) = O(n)
 */
public class FindKthLargest {

    public int findKthLargest(int[] nums, int k) {
        int len = nums.length;
        int left = 0;
        int right = len - 1;

        // 转换一下，第 k 大元素的索引是 len - k
        int target = len - k;

        while (true) {
            int index = partition(nums, left, right);
            if (index == target) {
                return nums[index];
            } else if (index < target) {
                left = index + 1;
            } else {
                assert index > target;
                right = index - 1;
            }
        }
    }

    /**
     * 在 nums 数组的 [left, right] 部分执行 partition 操作，返回 nums[i] 排序以后应该在的位置
     * 在遍历过程中保持循环不变量的语义
     * 1、(left, k] < nums[left]
     * 2、(k, i] >= nums[left]
     *
     * @param nums
     * @param left
     * @param right
     * @return
     */
    public int partition(int[] nums, int left, int right) {
        int pivot = nums[left];
        int j = left;
        for (int i = left + 1; i <= right; i++) {
            if (nums[i] < pivot) {
                // 小于 pivot 的元素都被交换到前面
                j++;
                swap(nums, j, i);
            }
        }
        // 最后这一步不要忘记了
        swap(nums, j, left);
        return j;
    }

    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }

    private int quickSort(int[] nums, int left, int right, int target) {
        if (left > right)
            return -1;
        // 相等的时候 只有一个元素，应该怎么办呢？ 答案说返回，但是没有想明白
        if (left == right) {
            return nums[left];
        }
        Random random_num = new Random();
        int randomIndex = left + random_num.nextInt(right - left);
        swap(nums, left, randomIndex);
        int key = nums[left];
        int l = left, r = right;
        while (left < right) {
            while (left < right && nums[right] >= key)
                right--;
            if (left < right)
                nums[left++] = nums[right];
            while (left < right && nums[left] < key)
                left++;
            if (left < right)
                nums[right--] = nums[left];
        }
        nums[left] = key;
        if (left == target)
            return key;
        else if (left < target)
            return quickSort(nums, left + 1, r, target);
        else
            return quickSort(nums, l, left - 1, target);
    }

    public int findKthLargest1(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || k > nums.length)
            return -1;
        int index = nums.length - k;
        return quickSort(nums, 0, nums.length - 1, index);
    }

    @Test
    public void test() {
        Assertions.assertEquals(4, findKthLargest1(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4));
        Assertions.assertEquals(5, findKthLargest1(new int[]{3, 2, 1, 5, 6, 4}, 2));
        Assertions.assertEquals(5, findKthLargest(new int[]{5}, 1));
        Assertions.assertEquals(5, findKthLargest(new int[]{5, 5}, 1));
        Assertions.assertEquals(4, findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4));
    }
}

