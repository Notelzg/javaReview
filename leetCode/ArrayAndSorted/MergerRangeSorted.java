package ArrayAndSorted;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sun.util.resources.cldr.zh.CalendarData_zh_Hans_HK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 给出一个区间的集合，请合并所有重叠的区间。
 * 思路1，第一列已排序  时间复杂度 O(n) 空间复杂度O(n^2)
 * 如果集合的第一个元素是按照从小到大排序的，则直接使用
 * intervals【row+1][0] <= intervals[row][1] 进行判断，成功则进行合并，
 * 同时进行下次合并，知道条件失败，则完成一次合并
 * 示例 1:
 * 思路2，第一列未排序  时间复杂度 O(n) 空间复杂度O(n^2)
 * 使用hash初始化 第一列作为key，第二列为value
 * 如果value 小于等于 intervals【row+1][0] ， 则合并
 * 否则 作为一个新的key value
 * <p>
 * 输入: [[1,3],[2,6],[8,10],[15,18]]
 * 输出: [[1,6],[8,10],[15,18]]
 * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 */
public class MergerRangeSorted {
    /**
     * a = [1,4] b = [2,3]
     * 两个集合，排序后的集合
     * b[0] <= a[1],则可以进行排序，排序后的结果是 [a[0], Math.max(a[1], [b1])]
     * 因为已经排序了所以 a[0]一定是小于或者等于b[0],作为集合左边是没问题的
     * 但是对于右边的元素，需要取最大值才行
     * @param intervals
     * @return
     */
    public int[][] mergeSorted(int[][] intervals) {
        if (intervals.length < 2)
            return intervals;
        List<int[]> ans = new ArrayList<>();
        Arrays.sort(intervals, (a, b)->a[0]-b[0]);
        int i = 0;
        while (i < intervals.length){
            int left = intervals[i][0];
            int right = intervals[i][1];
            while (i < intervals.length && intervals[i + 1][0] <= intervals[i][1]){
                i++;
                right = Math.max(right, intervals[i + 1][1]);
            }
            ans.add(new int[]{left, right});
            i++;
        }
        return ans.toArray(new int[0][]);
    }
    /**
     * 思路1
     *
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        if (intervals.length < 2)
            return intervals;
        int len = intervals.length;
        for (int i = 0; i < intervals.length; i++){
            for (int j = i + 1; j < intervals.length; j++){
                if (intervals[i] == null)
                    continue;
                if (intervals[j][0] <= intervals[i][1] && intervals[j][1] >= intervals[i][0]){
                    intervals[j][0] = intervals[i][0] > intervals[j][0] ? intervals[j][0] : intervals[i][0];
                    intervals[j][1] = intervals[i][1] > intervals[j][1] ? intervals[i][1] : intervals[j][1];
                    len--;
                    intervals[i] = null;
                }
            }
        }
        int[][] ans = new int[len][2];
        for (int i = intervals.length - 1; i >= 0; i--){
           if (intervals[i] != null)
                ans[--len]   = intervals[i];
        }
        return ans;
    }

    /**
     * 思路2
     * 先对第一列进行排序，同时使用 hash存储，第一列为key，保持集合
     * 遍历排序后的第一列，while(col[i] <= hash.get(col[i-1])){
     * i++
     * }
     *
     * @param intervals
     * @return
     */
    public int[][] mergeHash(int[][] intervals) {
        if (intervals.length < 2)
            return intervals;
        int start = 0;
        int[] col = new int[intervals.length];
        HashMap<Integer, Integer> hash = new HashMap<>(intervals.length);
        for (int i = 0; i < intervals.length; i++) {
            if (hash.containsKey(intervals[i][0])) {
                if (intervals[i][1] > hash.get(intervals[i][0])) {
                    hash.put(intervals[i][0], intervals[i][1]);
                }
                start++;
                continue;
            }
            hash.put(intervals[i][0], intervals[i][1]);
            col[i] = intervals[i][0];
        }
        Arrays.sort(col);
        int i = start + 1;
        //初始化
        int count = 0;
        intervals[count][0] = col[i - 1];
        intervals[count++][1] = hash.get(col[i - 1]);
        while (i < intervals.length) {
            //如果可以合并则合并，不能合并则插入
            if (col[i] <= hash.get(col[i - 1])) {
                // 应该考虑1，4,  2，3这种情况, ☝️应该存入 2，4
                if (hash.get(col[i]) < hash.get(col[i - 1]))
                    hash.put(col[i], hash.get(col[i - 1]));
                count--;
                intervals[count][1] = hash.get(col[i]);
            } else {
                intervals[count][0] = col[i];
                intervals[count][1] = hash.get(col[i]);
            }
            count++;
            i++;
        }
        int[][] ans = new int[count][2];
        for (int j = 0; j < count; j++) {
            System.arraycopy(intervals[j], 0, ans[j], 0, 2);
        }
        return ans;
    }

    @Test
    public void test() {
        String s = "{{4,5},{2,4},{4,6},{3,4},{0,0},{1,1},{3,5},{2,2}}";

        for (int[] ints : mergeSorted(new int[][]{{4, 5}, {2, 4}, {4, 6}, {3, 4}, {0, 0}, {1, 1}, {3, 5}, {2, 2}})) {
            System.out.println(Arrays.toString(ints));
        }
        System.out.println();

        for (int[] ints : mergeHash(new int[][]{{1, 4}, {1, 5}})) {
            System.out.println(Arrays.toString(ints));
        }
        System.out.println();

        for (int[] ints : mergeHash(new int[][]{{1, 4}, {1, 4}})) {
            System.out.println(Arrays.toString(ints));
        }
        System.out.println();
        for (int[] ints : mergeHash(new int[][]{{2, 3}, {5, 5}, {2, 2}, {3, 4}, {3, 4}})) {
            System.out.println(Arrays.toString(ints));
        }
        System.out.println();
        for (int[] ints : mergeHash(new int[][]{{1, 4}, {2, 3}})) {
            System.out.println(Arrays.toString(ints));
        }
        System.out.println();
        for (int[] ints : mergeHash(new int[][]{{1, 4}, {0, 4}})) {
            System.out.println(Arrays.toString(ints));
        }
        System.out.println();
        for (int[] ints : mergeHash(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}})) {
            System.out.println(Arrays.toString(ints));
        }
        System.out.println();
        /**
         * 输入: [[1,4],[4,5]]
         * 输出: [[1,5]]
         * 解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。
         */
        for (int[] ints : mergeHash(new int[][]{{1, 4}, {4, 5}})) {
            System.out.println(Arrays.toString(ints));
        }
    }
}
