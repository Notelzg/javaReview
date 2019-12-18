package ArrayAndSorted;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 给定一个包含了一些 0 和 1的非空二维数组 grid , 一个 岛屿 是由四个方向 (水平或垂直) 的 1 (代表土地) 构成的组合。你可以假设二维矩阵的四个边缘都被水包围着。
 *
 * 找到给定的二维数组中最大的岛屿面积。(如果没有岛屿，则返回面积为0。)
 */
public class MaxAreaOfLand {
    private int getMaxArea(int i, int j, int[][] grid){
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length)
            return 0;
        if (1 == grid[i][j]){
            //遍历之后设置为0，不再进行二次遍历
            grid[i][j] = 0;
            return  1 +  getMaxArea(i+1, j, grid)
                      +  getMaxArea(i-1, j, grid)
                      +  getMaxArea(i, j + 1, grid)
                      +  getMaxArea(i, j - 1, grid);

        }
        return 0;
    }

    /**
     * 解题思路，根据题目描述，在四个方向上进行查找最大的岛屿面积，说实话第一次没看懂题目，看了答案之后才知道。
     * 从当前节点的 上下左右，四个方向进行深度遍历，获取所有连接的岛屿。
     * 通过递归的思想进行所有节点的面积的遍历，取出最大的面积
     * 时间复杂度 O(n*n*
     * 空间复杂度
      * @param grid
     * @return
     */
    public int maxAreaOfIsland(int[][] grid) {
        int area = 0;
        for (int i = 0; i < grid.length; i++) //行
            for (int j = 0; j < grid[0].length; j++) //列
                if (grid[i][j] == 1) //岛屿
                    area = Math.max(area, getMaxArea(i, j, grid));
        return area;
    }

    @Test
    public void test(){
        Assertions.assertEquals(0, maxAreaOfIsland(new int[][]
                {{0,0,0,0,0,0,0,0}}));
        Assertions.assertEquals(6, maxAreaOfIsland(new int[][]{
                {0,0,1,0,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,1,1,0,1,0,0,0,0,0,0,0,0},
                {0,1,0,0,1,1,0,0,1,0,1,0,0},
                {0,1,0,0,1,1,0,0,1,1,1,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,1,1,0,0,0,0}}));
    }
}
