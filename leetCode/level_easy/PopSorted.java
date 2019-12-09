package level_easy;

import org.junit.jupiter.api.Test;



public class PopSorted {
    private  static  void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    public static  void findPairs(int[] nums, int k) {
        for (int i = nums.length - 1; i > 0; i--)
            for (int j = 0; j < i; j++) {
                if (nums[i] < nums[j])
                    swap(nums, i, j);

            }
        for (int i1 = 0; i1 < nums.length; i1++)
            System.out.println(nums[i1]);
    }

    @Test
    public void test(){
        PopSorted.findPairs(new int[]{3, 1, 4, 1, 5}, 2);
    }
}
