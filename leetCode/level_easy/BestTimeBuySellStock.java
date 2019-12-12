package level_easy;

import org.junit.jupiter.api.Test;

/**
 *给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 *
 * 如果你最多只允许完成一笔交易（即买入和卖出一支股票），设计一个算法来计算你所能获取的最大利润。
 *
 * 注意你不能在买入股票前卖出股票。
 *
 * 输入: [7,1,5,3,6,4]
 * 输出: 5
 * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
 *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * buy卖股票的时候 交易次数-1, 所引起的变化,进行整体分析,
 * 卖的时候减一会导致, 交易次数为0时,可以持有股票,导致了case base和买的时候减1发生了改变
 *zgli, 获取股票投资最高收益, 使用动态规划穷举例所有情况,特别需要注意的是sell buy交易次数减一,
 * 会导致base case发生变化,因为如果buy的时候减一
 * dp[i][0][1] = -infinity(交易次数为0,则不可能发生交易所以其值不存在), 但是如果sell的时候减一,
 * dp[i][0][1], 是存在的,可能是dp[i][1][0]的时候购买股票,
 * 存在 dp[i][0][1] = max(dp[i-1][0][1], dp[i-1][1][0] - price[i], dp[i[[0][0] = dp[i-1][0][0] = 0
 *
 * 具体分析如下 sell
 *
 * T[-1][k][0] = 0
 * T[-1][k][1] = -Infinity
 * T[i][0][0] = 0
 * T[i][0][1] = max(T[i-1][0][1], T[i-1][0][0] - prices[i])
 *
 * The first two terms have the same explanation as before: no stock no profit, and it is impossible for us to hold 1 stock if no stock is available.
 *
 * The third term T[i][0][0] = 0 is understood as follows: if k = 0, we have no way to sell any stock since selling will take one transaction (per our interpretation above). Therefore the profit won't change with i (since no transaction is performed) and if we trace all the way back to the beginning of the very first day (i = -1), we know the profit is 0.
 *
 * The last term T[i][0][1] = max(T[i-1][0][1], T[i-1][0][0] - prices[i]) is, however, different from our original base case and is understood as follows: even if now k = 0, we can still buy stocks since buying a stock does not take any transaction (again per our interpretation above). Therefore despite k = 0, we still need to choose the maximum profits from the two actions -- rest and buy, corresponding to the two entries in the max function.
 *
 * So if I modify your code like below, it works as expected:
 *
 * public int maxProfit(int[] prices) {
 *     int[][][] T = new int[prices.length+1][3][2];
 *
 *     T[0][0][1] = T[0][1][1] = T[0][2][1] = Integer.MIN_VALUE;
 *
 *     for (int i = 1; i <= prices.length; i++){
 *         T[i][0][0] = 0;
 *         T[i][0][1] = Math.max(T[i-1][0][1], T[i-1][0][0] - prices[i-1]); // forced to use recurrence equation here!
 *         T[i][1][0] = Math.max(T[i-1][1][0], T[i-1][0][1] + prices[i-1]);
 *         T[i][1][1] = Math.max(T[i-1][1][1], T[i-1][1][0] - prices[i-1]);
 *         T[i][2][0] = Math.max(T[i-1][2][0], T[i-1][1][1] + prices[i-1]);
 *         T[i][2][1] = Math.max(T[i-1][2][1], T[i-1][2][0] - prices[i-1]);
 *     }
 *
 *     return Math.max(T[prices.length][2][0], T[prices.length][2][1]);
 * }
 * Now you might be wondering why the two interpretations have different base cases. Here is an explanation.
 *
 * If we take the interpretation such that buying a stock takes one transaction while selling does not, following the same logic as above, the base cases should really be:
 *
 * T[-1][k][0] = 0
 * T[-1][k][1] = -Infinity
 * T[i][0][1] = -Infinity
 * T[i][0][0] = max(T[i-1][0][0], T[i-1][0][1] + prices[i])
 *
 * The first two terms again have the same meaning as before.
 *
 * For the third term, no transaction is possible now since k = 0 and buying will take one transaction, so the profit won't change with i. If again we trace all the way back to the beginning of the first day (i = -1), the profit is found to be -Infinity.
 *
 * For the last term, however, selling is still possible even if k = 0 since selling does not take any transactions. Therefore we still need to choose the maximum profits from the two actions -- rest and sell.
 *
 * Then why can we set T[i][0][0] = 0 instead of using the last equation above? The reason is due to the fact that if T[i][0][1] = -Infinity, then T[i-1][0][1] + prices[i] will always be less than T[i-1][0][0], thus the max function will always be evaluated to T[i-1][0][0], that is, we always have T[i][0][0] = T[i-1][0][0]. And if we trace all the way back to the beginning (i = -1), this value is found to be 0, therefore we can set T[i][0][0] = 0. However, this result does not hold for the other interpretation, which is why we are forced to use the recurrence equation instead.
 *
 * Lastly, for comparison, here is the solution using explicitly the recurrence equation for T[i][0][0] in the base cases, instead of setting it directly to 0:
 *
 * public int maxProfit(int[] prices) {
 *     int[][][] T = new int[prices.length+1][3][2];
 *
 *     T[0][0][1] = T[0][1][1] = T[0][2][1] = Integer.MIN_VALUE;
 *
 *     for (int i = 1; i <= prices.length; i++) {
 *         T[i][0][1] = Integer.MIN_VALUE;
 *         T[i][0][0] = Math.max(T[i-1][0][0], T[i-1][0][1] + prices[i-1]); // use recurrence equation instead of setting it directly to 0
 *         T[i][1][1] = Math.max(T[i-1][1][1], T[i-1][0][0] - prices[i-1]);
 *         T[i][1][0] = Math.max(T[i-1][1][0], T[i-1][1][1] + prices[i-1]);
 *         T[i][2][1] = Math.max(T[i-1][2][1], T[i-1][1][0] - prices[i-1]);
 *         T[i][2][0] = Math.max(T[i-1][2][0], T[i-1][2][1] + prices[i-1]);
 *     }
 *
 *     return Math.max(T[prices.length][2][0], T[prices.length][2][1]);
 * }
 */
public class BestTimeBuySellStock {

    public int maxProfit(int[] prices) {
        int max = 0;
        int start = 0;
        for (int i = 1; i < prices.length; i++){
            int subValue = prices[i] - prices[start];
            if ( subValue > max){
               max = prices[i]  - prices[start];
            }else if (subValue < 0){
                start = i;
            }
        }
        return max;
    }
    public int all_K2_O1(int[] prices){
        int n = prices.length;
        int[][][] dp = new int[n][2][2];
        int dp_i_0_0 = 0;
        int dp_i_0_1 = Integer.MIN_VALUE;
        int dp_i_1_0 = 0;
        int dp_i_1_1 = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++){
                dp_i_0_0 = Math.max(dp_i_0_0, dp_i_0_1 + prices[i]);
                dp_i_0_1 = Math.max(dp_i_0_1, dp_i_0_0 - prices[i]);
                dp_i_1_0 = Math.max(dp_i_1_0, dp_i_1_1 + prices[i]);
                dp_i_1_1 = Math.max(dp_i_1_1, dp_i_1_0 - prices[i]);
        }
        return dp[prices.length - 1][1][0];
    }
    /**
     *  空间复杂度 O(n^2)
     *  交易次数 k = 2
     * @param prices
     * @return
     */
    public int all_K2(int[] prices){
        int n = prices.length;
        int[][][] dp = new int[n][2][2];
        for (int i = 0; i < n; i++){
            for (int k = 0; k < 2; k++) {
                if (0 == i) {
                    dp[0][0][0] = 0;
                    dp[0][0][1] = -prices[0];
                    dp[0][1][0] = 0;
                    dp[0][1][1] = -prices[0];
                    continue;
                }
                dp[i][k][0] = Math.max(dp[i - 1][k][0], dp[i - 1][k][1] + prices[i]);
                dp[i][k][1] = Math.max(dp[i - 1][k][1], dp[i-1][k-1][0]-prices[i]);
            }

        }
        return dp[prices.length - 1][1][0];
    }

    /**
     *  空间复杂度 O(n^2)
     *  交易次数 k = 1
     * @param prices
     * @return
     */
    public int all(int[] prices){
       int n = prices.length;
       int[][] dp = new int[prices.length][2];
       for (int i = 0; i < prices.length; i++){
           if ( 0 == i){
               dp[0][0] = 0;
               dp[0][1] = -prices[0];
               continue;
           }
          dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
          dp[i][1] = Math.max(dp[i - 1][1],  -prices[i]);

       }
       return dp[prices.length - 1][0];
    }
    /**
     *  空间复杂度 O(n^1)
     * @param prices
     * @return
     */
    public int allO1(int[] prices){
        int dp_i_0  = 0;
        int dp_i_1  = Integer.MIN_VALUE;
        for (int i = 0; i < prices.length; i++){
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
            dp_i_1 = Math.max(dp_i_1,  -prices[i]);
        }
        return dp_i_0;
    }

    @Test
    public void test(){
        System.out.println(allO1(new int[]{7, 1, 5, 3, 6, 4}));
        System.out.println(allO1(new int[]{2,1,2,1,0,1,2}));
    }

}
