package DynamicPrograming;

import ListAanTree.utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 * 如果你最多只允许完成一笔交易（即买入和卖出一支股票），设计一个算法来计算你所能获取的最大利润。
 * 注意你不能在买入股票前卖出股票。
 *
 */
public class StockMaxProfit {
    /**
     * 思路动态规划来解决
     * 天数，交易次数，转态 买股票、卖股票、持有股票，是一个循环，使用状态表示现在是否有股票, 0 没有股票 1， 有股票
     * 必须在有股票的情况下 才能卖股票，必须在有交易次数和不持有股票的时候购买股票
     * 买的时候 交易次数减1,  导致存在 k = 0 ，但是持有状态 1 的情况
     * 卖的时候交易次数减1,  ans[-1][k][0] = 0, ans[-1][k][1] 不存在
     * ans[i][0][1] = ans[i][k][0] = 0, ans[i][k-1][1] =
     *
     * 第 i 天 股票状态为0存在两种情况，i-1天 不持有股票 i天没有买 ，i-1天 持有股票 i天卖掉,最大利润取最大值
     * ans[i][k][0] = max( ans[i-1][k][0] , ans[i-1][k][1] + price[i])
     * 第 i 天 股票状态为1存在两种情况，i-1天 持有股票 i天继续持有 ，i-1天 持有没有股票 i天买股票，利润减去股票钱
     * 在买的时候交易次数减去1，
     * ans[i][k][1] = max( ans[i-1][k][1] , ans[i-1][k - 1][0] - price[i])
     * base case
     * 当 i = 0 时
     * 头一天交易次数为k 不持有股票利润为 0, 负一天交易次数为1不存在所以是 -innity
     * ans[0][k][0] = max( ans[-1][k][0] , ans[-1][k][1] + price[i]) = 0
     * ans[0][k][1] = max( ans[-1][k][1] , ans[-1][k][0] - price[i]) = -price
     * 当 k = 0 时 表示没有交易次数了，是无法进行交易的
     *  所asn[i][k][0] = 0, asn[i][0][1] = -infinity
     *  当k = 1时
     * dp[i][1][0] = max(dp[i-1][1][0], dp[i-1][1][1] + prices[i])
     * dp[i][1][1] = max(dp[i-1][1][1], dp[i-1][0][0] - prices[i])
     *          因为   dp[i-1][0][0] = 0;
     *          所以：
     * dp[i][1][1] = max(dp[i-1][1][1],  - prices[i])
     * 由于所以 k =1 ，所以可以省略k
     * dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
     * dp[i][1] = max(dp[i-1][1],  - prices[i])
     * 当 k = 2 时 ,每一天有四种转态，如下
     * dp[i][2][1] = max(dp[i-1][2][1], dp[i-1][1][0] - prices[i])
     * dp[i][2][0] = max(dp[i-1]2[0], dp[i-1][2][1] + prices[i])
     * dp[i][1][1] = max(dp[i-1][1][1], dp[i-1][1-1=0][0] - prices[i])
     * dp[i][1][0] = max(dp[i-1][1][0], dp[i-1][1][1] + prices[i])
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        //由于k =n, 尽可能多的完成交易，由于每次必须先卖出再买单独进行一次交易，所以
        //尽可能多的进行交易的时候，其实 k - 1 = k是没有问题的，所以和k=1一样，可以
        //化简，把k去掉，但是需要注意的是 k=1时， dp[i][k-1][0] = 0,
        //但是 k 趋近于无穷大的时候， dp[i][k-1][0] 就不是0了，所以
        //使用一个dpi0_cop
        int len = prices.length;
        if (len < 1)
            return 0;
        int dpi1 = Integer.MIN_VALUE;
        int dpi0 = 0;
        int dpi0_copy;
        for (int key : prices){
            dpi0_copy = dpi0;
            dpi0 = Math.max(dpi0, dpi1 + key);
            dpi1 = Math.max(dpi1, dpi0_copy-key);
        }
        return dpi0;
    }
    public int maxProfitK1(int[] prices) {
        //由于k =1
        int len = prices.length;
        if (len < 1)
            return 0;
        int dpi1 = Integer.MIN_VALUE;
        int dpi0 = 0;
        for (int key : prices){
            dpi0 = Math.max(dpi0, dpi1 + key);
            dpi1 = Math.max(dpi1, -key);
        }
        return dpi0;
    }
    public int maxProfitK2(int[] prices) {
       //由于k =2
        int len = prices.length;
        if (len < 1)
            return 0;
        int dp_021 = -prices[0];
        int dp_020 = 0;
        int dp_011 = -prices[0];
        int dp_010 = 0;
        // 根据 dp公式，dp[i] 只依赖 dp[i-1[ 所以 只记录 dp-1就可以
        for (int i = 1; i < len; i++){
            dp_020 = Math.max(dp_020, dp_021 + prices[i]);
            dp_021 = Math.max(dp_021, dp_010 - prices[i]);
            dp_010 = Math.max(dp_010, dp_011 + prices[i]);
            dp_011 = Math.max(dp_011,  - prices[i]);
        }
        return dp_020;
    }
    public int maxProfit(int[] prices, int k) {
        int len = prices.length;
        int ans[][][] = new int[len][k + 1][2];
        for (int i = 0; i < len; i++ ) {
            for (int j = k; j > 0; j--) {
                if (i == 0){
                    ans[i][j][0] = 0;
                    ans[i][j][1] = -prices[i];
                }else {
                    ans[i][j][0] = Math.max(ans[i - 1][j][0], ans[i - 1][j - 1][1] + prices[i]);
                    ans[i][j][1] = Math.max(ans[i - 1][j - 1][1], ans[i - 1][j][0] - prices[i]);
                }
            }
        }
        return ans[len-1][0][0];
    }
    @Test
    public void test(){
        Assertions.assertEquals(0, maxProfit(new int[]{}));
        Assertions.assertEquals(5, maxProfit(utils.str2intArr("7,1,5,3,6,4]")));
        Assertions.assertEquals(0, maxProfit(utils.str2intArr("7,6,4,3,1")));
    /*
    输入: [7,1,5,3,6,4]
输出: 5
解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
示例 2:

输入: [7,6,4,3,1]
输出: 0
解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
     */
    }
}
