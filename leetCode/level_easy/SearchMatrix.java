package level_easy;

import org.junit.jupiter.api.Test;

/**
 * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：
 *
 * 每行的元素从左到右升序排列。
 * 每列的元素从上到下升序排列。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-a-2d-matrix-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class SearchMatrix {
    public boolean searchMatrixOptional(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0)
            return false;
        int row = matrix.length - 1;
        int col = 0;
        while (row >=0 && col < matrix[0].length){
            if (matrix[row][col] > target)
                row--;
            else  if (matrix[row][col] < target)
                col++;
            else
                return  true;
        }
        return  false;
    }
    int[][] matrix ;
    int target;
    private boolean searchRecursive(int up, int down, int left, int right){
        if (left > right || up > down )
            return false;
        if (target < this.matrix[up][left] || target > this.matrix[down][right])
            return false;
        int mid = (left + right)/2;
        int row = up;
        while (row <= down && this.matrix[row][mid] <= target){
            if (this.matrix[row][mid] == target)
                return true;
            row++;
        }
        return searchRecursive(row, down, left, mid -1) || searchRecursive(up, row -1, mid + 1, right);
    }
    public boolean searchMatrix(int[][] matrix, int target) {
        if (null == matrix || matrix.length == 0)
            return false;
        this.matrix = matrix;
        this.target = target;
        return  searchRecursive(0, matrix.length - 1, 0, matrix[0].length - 1);
    }
    private boolean search(int[][] matrix, int target, int start, boolean vertical){
        int l = start;
        int r = vertical ? matrix[0].length - 1 : matrix.length - 1;
        int mid;
        while (l < r){
           mid = (l + r) / 2; //这个点是对角线上的点
           if (vertical){
              //row
              if (target < matrix[start][mid] )
                  r = mid - 1;
              else if (target > matrix[start][mid])
                  l = mid + 1;
              else
                  return  true;
           }else {
              //column
               if (target < matrix[mid][start] )
                   r = mid - 1;
               else if (target > matrix[mid][start])
                   l = mid + 1;
               else
                   return  true;
           }
        }
        return  false;
    }
    public boolean searchMatrix1(int[][] matrix, int target) {
        if (null == matrix || matrix.length == 0)
            return false;
        // 找到矩阵短的那一边，是为了从矩阵的对角线对矩阵进行搜索，相当于每次搜索，使用二分搜索行和列，
        // 由于使用对角线上面的点作为开始节点，所以避免了搜索重复数据
        int shortLen = matrix.length > matrix[0].length ? matrix[0].length : matrix.length;
        boolean vrs;
        boolean hrs;
        for (int i = 0; i < shortLen; i++){
           vrs = search(matrix, target, i , true) ;
           hrs = search(matrix, target, i , false) ;
           if (vrs || hrs)
               return true;
        }
        return false;
    }
    @Test
    public void test(){
        searchMatrixOptional(new int[][]{
         {1,4,7,11,15}
        ,{2,5,8,12,19}
        ,{3,6,9,16,22}
        ,{10,13,14,17,24}
        ,{18,21,23,26,30}}, 5);

    }
}
