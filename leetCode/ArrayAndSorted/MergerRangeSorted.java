package ArrayAndSorted;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sun.util.resources.cldr.zh.CalendarData_zh_Hans_HK;

import java.util.Arrays;
import java.util.HashMap;

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
 *
 * 输入: [[1,3],[2,6],[8,10],[15,18]]
 * 输出: [[1,6],[8,10],[15,18]]
 * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 */
public class MergerRangeSorted {
    /**
     * 思路1
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {

//        for (int i = 0; i < intervals.length; i++){
//            while ()
//        }
        return null;
    }

    /**
     * 思路2
     * 先对第一列进行排序，同时使用 hash存储，第一列为key，保持集合
     * 遍历排序后的第一列，while(col[i] <= hash.get(col[i-1])){
     *                          i++
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
        for (int i = 0; i < intervals.length; i++){
            if (hash.containsKey(intervals[i][0])){
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
        if (intervals.length - start == 1) {
            return new int[][]{{col[intervals.length - 1], hash.get(col[intervals.length - 1])}};
        }
        int i = start + 1;
        int count = 0;
        int copyI ;
        while (i < intervals.length){
            copyI = i;
            while ( i < intervals.length && col[i] <= hash.get(col[i-1])){
                // 应该考虑1，4  2，3这种情况
                if (hash.get(col[i]) < hash.get(col[i-1]))
                    hash.put(col[i], hash.get(col[i-1]));
                i++;
            }
            // 如果进行合并则只有一个，不合并则需要
            if (copyI == i){
                intervals[count][0] = col[i - 1];
                intervals[count++][1] = hash.get(col[i-1]);
                intervals[count][0] = col[i];
                intervals[count++][1] = hash.get(col[i]);
            }else {
                intervals[count][0] = col[copyI - 1];
                intervals[count++][1] = hash.get(col[i-1]);
                if (i == intervals.length - 1){
                    intervals[count][0] = col[i];
                    intervals[count++][1] = hash.get(col[i]);
                }
            }
            i++;
        }
        int[][] ans = new int[count][2];
        for (int j = 0; j < count; j++){
            System.arraycopy(intervals[j], 0, ans[j], 0, 2);
        }
        return  ans;
    }

    @Test
    public void test(){
        for (int[] ints : mergeHash(new int[][]{{1, 4}, {1, 5}})) {
            System.out.println(Arrays.toString(ints));
        }

        for (int[] ints : mergeHash(new int[][]{{1, 4}, {1, 4}})) {
            System.out.println(Arrays.toString(ints));
        }
        for (int[] ints : mergeHash(new int[][]{{2,3},{5,5},{2,2},{3,4},{3,4}})) {
            System.out.println(Arrays.toString(ints));
        }
        for (int[] ints : mergeHash(new int[][]{{1, 4}, {2, 3}})) {
            System.out.println(Arrays.toString(ints));
        }
        for (int[] ints : mergeHash(new int[][]{{1, 4}, {0, 4}})) {
            System.out.println(Arrays.toString(ints));
        }
        for (int[] ints : mergeHash(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}})) {
            System.out.println(Arrays.toString(ints));
        }
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
