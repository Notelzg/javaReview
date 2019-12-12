package level_easy;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

/**
 * 给定一个按非递减顺序排序的整数数组 A，返回每个数字的平方组成的新数组，要求也按非递减顺序排序。
 * 示例 1：
 * 输入：[-4,-1,0,3,10]
 * 输出：[0,1,9,16,100]
 *
 */
public class SquaresSortedArray {
    /**
     * 思路: 两个指针,p1 p2 ,分别指向负数和正数
     * 对比平方值, 采用插入排序思想,
     * 时间复杂度 O(n+n/2)
     * 空间复杂度 O(n)
     * @param A
     * @return
     */
    public int[] sortedSquares(int[] A) {
        Objects.requireNonNull(A);
        if (A[0] >= 0 ) {
            for (int i = 0; i < A.length; i++)
                A[i] = A[i] * A[i];
            return A;
        }
        if (A[A.length - 1] <= 0) {
            int[] temp = new int[A.length];
            System.arraycopy(A, 0, temp, 0, A.length - 1);
            int len = A.length - 1;
            for (int i = 0; i < A.length; i++) {
                A[i] = temp[len] * temp[len--];
            }
            return A;
        }
        int[] ans = new int[A.length];
        int p1 = 0, p2 = 0, p1Len = 0;
        // 找到第一个非负值
        while (p2 < A.length && A[p2++] < 0);
        p2--;
        p1 = p2 - 1;
        int i = 0;
        while (p1 >=0 && p2 < A.length)
            ans[i++] = (A[p1] + A[p2] >0)? A[p1] * A[p1--]: A[p2] * A[p2++];
        while (p1 >= 0)
            ans[i++] = A[p1] * A[p1--];
        while (p2 < A.length)
            ans[i++] = A[p2] * A[p2++];
        return ans;
    }
    @Test
    public void  test(){
        /**
         * 输入：{-4,-1,0,3,10}
         * 输出：{0,1,9,16,100}
         *
         * 输入：{-7,-3,2,3,11}
         * 输出：{4,9,9,49,121}
         */
        int[] ans = sortedSquares(new int[]{-4,-1,0,3,10});
        for (int an : ans) {
            System.out.println(an);
        }
        ans = sortedSquares(new int[]{-7,-3,2,3,11});
        for (int an : ans) {
            System.out.println(an);
        }
        ans = sortedSquares(new int[]{-1,1});
        for (int an : ans) {
            System.out.println(an);
        }
    }
}
