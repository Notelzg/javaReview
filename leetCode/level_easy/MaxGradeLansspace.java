package level_easy;

import org.junit.jupiter.api.Test;

/**
 * 给定正整数数组 A，A[i] 表示第 i 个观光景点的评分，并且两个景点 i 和 j 之间的距离为 j - i。
 *
 * 一对景点（i < j）组成的观光组合的得分为（A[i] + A[j] + i - j）：景点的评分之和减去它们两者之间的距离。
 *
 * 返回一对观光景点能取得的最高分。
 * 示例：
 * 输入：[8,1,5,2,6]
 * 输出：11
 * 解释：i = 0, j = 2, A[i] + A[j] + i - j = 8 + 5 + 0 - 2 = 11
 */
public class MaxGradeLansspace {
    public int maxScoreSightseeingPair(int[] A) {
        int pre_i_0 = 0;
        int j_j = 0;
        int ans = 0;
        for (int i = 0; i < A.length; i++){
           ans = Math.max(ans, pre_i_0 + A[i] - i) ;
           pre_i_0 = Math.max(pre_i_0, A[i] + i);
        }
        return ans;
    }
    /**
     * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：
     *
     * 每行的元素从左到右升序排列。
     * 每列的元素从上到下升序排列。
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        return true;
    }
    @Test
    public void test(){
        System.out.println(maxScoreSightseeingPair(new int[]{7, 8, 8, 10}));
        System.out.println(maxScoreSightseeingPair(new int[]{8,1,5,2,6}));
    }
}
