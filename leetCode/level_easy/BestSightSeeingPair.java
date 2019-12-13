package level_easy;

import org.junit.jupiter.api.Test;


/**
 * 给定正整数数组 A，A[i] 表示第 i 个观光景点的评分，并且两个景点 i 和 j 之间的距离为 j - i。
 *
 * 一对景点（i < j）组成的观光组合的得分为（A[i] + A[j] + i - j）：景点的评分之和减去它们两者之间的距离。
 *
 * 返回一对观光景点能取得的最高分。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-sightseeing-pair
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 首先需要明确一个事情
 *  A[i] + A[j] + i - j）实际上是 (A[i] + i), (A[j]-j), 相当于a + b
 *  可以看做是从一个无序的列表中找到两个最大值,实际上题目的考点我个人理解也是这个意思.
 *  如果这样看题目的话,其实其购买股票的最大收益那道题思路是一样的,把地点当做一个状态
 *  观光组合是两个地方,所以次数k 为2, 当然使用股票的那个穷举状态解法是可以解决,k = 1, k=3
 *  咱们是解决一类问题,不是解决一个问题,这个解决范娜太牛皮了,如果你没看过,下面是地址
 *  https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/solution/yi-ge-fang-fa-tuan-mie-6-dao-gu-piao-wen-ti-by-l-3/
 *
 *  根据状态的解法是这样的
 *  for i in n 地点
 *      for j in k 组合的个数
 *  总共有n*k 这种组合,
 *  当 k = 2时
 *  dp[i][2] = max(dp[i-1][2], dp[i-1][1] + A[i] -i)
 *  // 第i个景点最多观光2次的最高分等于 前一个景点你的最多两次和前一个最多一次, 最多一次表明之前已经去过一个景点了,所以需要 减去 i
 *  dp[i][1] = max(dp[i-1][1], dp[i-1][0] + A[i] +i)
 *  // 第i个景点最多观光1次的最高分等于 前一个景点你的最多1次和前一个最多0次, 最多0次意思是不能去任何观光点了.
 *  base case
 * dp[-1][-1] = -infinity
 * //景点不存在的时候, 去了一个不存在的景点, 所以是不发生事件.
 * dp[-1][k] = 0
 * // 景点不存在的时候,最多有k次,是合理的,所以得分为 0
 *  单i = 0时需要考虑的问题
 *  dp[0][2] = max(dp[-1][2], dp[-1][1] + A[0] - 0)
 *           = max(0, A[0])
 *           = A[0]
 * dp[0][1] = Math.max(dp[-1][1], dp[-1][0] + A[0] + 0) = A[0];
 *
 * dp[i][0] = Math.max(dp[-1][0], dp[-1][-1] + A[0] + 0)
 *          = dp[-1][0]
 *          = 0
 *
 * 当 k = 1时,就是取最大值
 * 当 k = 3时,自动扩充状态就行.
 *
 * 其实本题和股票最高收益的解题思路是一样的,不过更简单而已,
 * 股票的最佳收益的题使用动态规划的,还需要考虑股票的 买 卖 持有等操作, 而最佳观光组合是不需要考虑这个问题的
 * 只要考虑地点的个数就可以了,因为地点确定了,下标就确定了, A[i] + A[j] + i - j）实际上是
 * (A[i] + i), (A[j]-j),下标确定了 只能
 */
public class BestSightSeeingPair {
    /**
     * 本体的解法
     * @param A
     * @return
     */
    public int maxScoreSightseeingPair(int[] A) {
        int[][] dp = new int[A.length][3];
        for (int i = 0; i < A.length; i++){
            dp[i][0] = 0;
            if (i-1 == -1) {
                dp[i][2] = A[0];
                dp[i][1] = A[0];
                continue;
            }
           dp[i][2] = Math.max(dp[i-1][2], dp[i-1][1] + A[i] - i);
           dp[i][1] = Math.max(dp[i-1][1],  A[i] + i);
        }
        return dp[A.length -1][2];
    }
    public int maxScoreSightseeingPair1(int[] A) {
        int dp_i1 = A[0];
        int dp_i2 = A[0];
        for (int i = 1; i < A.length; i++){
            dp_i2 = Math.max(dp_i2, dp_i1 + A[i] - i);
            dp_i1 = Math.max(dp_i1,  A[i] + i);
        }
        return dp_i2;
    }



    @Test
    public void  test(){
        /**
         * 输入：[8,1,5,2,6]
         * 输出：11
         * 解释：i = 0, j = 2, A[i] + A[j] + i - j = 8 + 5 + 0 - 2 = 11
          */
        maxScoreSightseeingPair(new int[]{8,1,5,2,6});
    }
}
