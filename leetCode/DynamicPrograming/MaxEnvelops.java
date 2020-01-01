package DynamicPrograming;

import ListAanTree.utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 给定一些标记了宽度和高度的信封，宽度和高度以整数对形式 (w, h) 出现。当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
 * 请计算最多能有多少个信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
 * 说明:
 * 不允许旋转信封
 * 思路1，最简单的就是，先排序，对数组第一个元素进行排序，
 * 使用一个pre指针指向当前访问过的最大值，如果current > pre,则把pre = current，同时计数加1
 * 排序之后，e[i][0] > e[i-1][0] && e[i][1] > e[i-1][1]
 */
public class MaxEnvelops {
    public int maxEnvelopes(int[][] envelopes) {
        if (null == envelopes || envelopes.length == 0 || envelopes[0].length == 0)
            return 0;
        int len = envelopes.length;
        LinkedList<int[]> queen = new LinkedList<>();
        //如果w相等，则按照h降序排序
        Arrays.sort(envelopes, (a, b)-> a[0] == b[0] ? b[1]-a[1] : a[0] - b[0]);
        int[] dp = new int[len];
        dp[0] = envelopes[0][1];
        int end = 0;
        int left, right, mid;
        for (int i = 1; i < len; i++){
            if (envelopes[i][1] > dp[end]) {
                dp[++end] = envelopes[i][1];
            }
            else {
                left = 0;
                right = end;
                while (left < right){
                    mid = (left + right) >>> 1;
                    if (envelopes[i][1] > dp[mid])
                        left = mid + 1;
                    else
                        right = mid;
                }
                dp[left] = envelopes[i][1];
            }
        }
        return end+1;
    }

    @Test
    public void test(){
//        输入: envelopes = [[5,4],[6,4],[6,7],[2,3]]
//        输出: 3
//        解释: 最多信封的个数为 3, 组合为: [2,3] => [5,4] => [6,7]。
        Assertions.assertEquals(5, maxEnvelopes(utils.str2intArrArr("[[2,100],[3,200],[4,300],[5,500],[5,400],[5,250],[6,370],[6,360],[7,380]]")));
        Assertions.assertEquals(0, maxEnvelopes(utils.str2intArrArr("[[]]")));
        Assertions.assertEquals(3, maxEnvelopes(utils.str2intArrArr("[[5,4],[6,4],[6,7],[2,3]]")));
        Assertions.assertEquals(1, maxEnvelopes(utils.str2intArrArr("[[5,4],[6,4],[6,4],[2,4]]")));
        Assertions.assertEquals(1, maxEnvelopes(utils.str2intArrArr("[[5,4]]")));
    }
}
