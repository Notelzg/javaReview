package test;

import org.junit.jupiter.api.*;


public class EasyTest {
    public boolean searchMatrix(int[][] matrix, int target) {
        // start our "pointer" in the bottom-left
        int row = matrix.length - 1;
        int col = 0;
        while (row >=0 && col < matrix[0].length){
            if (matrix[row][col] < target)
                row--;
            else  if (matrix[row][col] > target)
                col++;

            else
                return true;
        }
        return  false;
    }

    @Test
    public void  test(){
    }
}
