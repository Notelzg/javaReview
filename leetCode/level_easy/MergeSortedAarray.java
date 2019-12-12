package level_easy;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 给定两个有序整数数组 nums1 和 nums2，将 nums2 合并到 nums1 中，使得 num1 成为一个有序数组。
 * <p>
 * 说明:
 * 初始化 nums1 和 nums2 的元素数量分别为 m 和 n。
 * 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
 * 输入:
 * nums1 = [1,2,3,0,0,0], m = 3
 * nums2 = [2,5,6],       n = 3
 * 输出: [1,2,2,3,5,6]
 */
public class MergeSortedAarray {

    /* 插入排序的思想 时间复杂度O(m + m) 空间复杂度O(m) */
    public void merge1(int[] nums1, int m, int[] nums2, int n) {
        int[] temp = new int[m];
        System.arraycopy(nums1, 0, temp, 0, m);
        int i = 0, j = 0;
        int index = 0;
        while (i < m && j < n)
            nums1[index++] = (temp[i] < nums2[j]) ? temp[i++] : nums2[j++];
        if (i < m)
            System.arraycopy(temp, i, nums1, index, m - i);
        if (j < n)
            System.arraycopy(nums2, j, nums1, index, n - j);
    }

    /* 插入排序的思想 时间复杂度O(m*m) 空间复杂度O(1) */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int index = 0;
        if (0 == n)
            return;
        if (0 == m || nums1[m - 1] <= nums2[0]) {
            for (int i = 0; i < n; i++)
                nums1[m++] = nums2[i];
            return;
        }


        for (int i = 0; i < n; i++) {
            index = m + i - 1;
            while (index >= 0 && nums2[i] < nums1[index]) {
                nums1[index + 1] = nums1[index];
                index--;
            }
            nums1[index + 1] = nums2[i];
        }
    }

    @Test
    public void test() {
        int[] nums1 = new int[]{2, 3, 5, 0, 0, 0};
        int[] nums2 = new int[]{5, 6, 7};
        merge1(nums1, 3, nums2, 3);
        for (int i : nums1) {
            System.out.println(i);
        }
    }
}
