package ArrayAndSorted;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
 * <p>
 * ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
 * <p>
 * 搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
 * <p>
 * 你可以假设数组中不存在重复的元素。
 * <p>
 * 你的算法时间复杂度必须是 O(log n) 级别。
 * <p>
 * 示例 1:
 */
public class SearchSpinArray {
    /**
     * 根据本题已经排序的特点可以知道  nums[left] < nums[mid]
     * @param nums
     * @param target
     * @return
     */
   public int searchBinarySimplify(int[] nums, int target) {
       if (nums == null || nums.length == 0) {
           return -1;
       }
      int left = 0;
      int right = nums.length - 1;
      int mid ;
      while (left <= right){
            mid = (left + right)/2;
            if (nums[mid] == target)
                return mid;
            //这个是为了处理，假如存在重复值, 小于说明left--mid 是有序的
            if (nums[left] <= nums[mid]){
                if (target >= nums[left] && target <= nums[mid])
                    right = mid - 1;
                else
                    left = mid + 1;
            }else { // left-mid包含自旋，所以mid-》len-1是升序排列的, 由于mid已经过了自旋点，所以如果 target 属于 mid-len-1
                    //则 肯定大于 nums[mid], 小于nums[0], 因为nums[0]是自旋点之后的数据肯定大于自旋点之前的数据
                if (target >= nums[mid] && target < nums[0])
                    left = mid + 1;
                else
                    right = mid - 1;
            }
      }
     return  -1;
   }


    private int findRoatePoint(int[] nums, int left, int right) {
        if (nums[left] < nums[right])
            return 0;
        int mid;
        while (left <= right) {
            mid = (left + right) / 2;
            if (nums[mid] > nums[mid + 1])
                return mid + 1;
            else {
                if (nums[mid] < nums[left])
                    right = mid - 1;
                else
                    left = mid + 1;
            }
        }
        return 0;
    }

    private int binarySearch(int left, int right, int[] nums, int target) {
        int mid ;
        while (left <= right) {
            mid = (left + right) / 2;
            if (target == nums[mid])
                return mid;
            else if (target > nums[mid])
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1;
    }

    public int serarhB(int[] nums, int target) {
        int ans = -1;
        int len = nums.length;
        if (nums.length == 0)
            return -1;
        if (nums.length == 1)
            return (nums[0] == target) ? 0 : -1;

        int roateIndex = findRoatePoint(nums, 0, len - 1);
        if (0 == roateIndex)
            ans =  binarySearch(0, len - 1, nums, target);
        else {
            if (target > nums[len - 1])
                ans =    binarySearch(0, roateIndex - 1, nums,  target);
            else
                ans =     binarySearch(roateIndex, len - 1, nums, target);
        }

        return ans;
    }

    /**
     * @param nums
     * @param target
     * @return
     */
    //正常搜索, 从后或者从前搜索 ,和上面的二分查找没有性能上的差别，
    public int search(int[] nums, int target) {
        int ans = -1;
        if (nums == null || nums.length == 0)
            return ans;
        if (nums.length == 1)
            return (nums[0] == target) ? 0 : -1;
        if (target < nums[0] && target > nums[nums.length - 1])
            return ans;
        int index;
        if (target >= nums[0]) {
            index = 0;
            while (index < nums.length - 1 && nums[index] <= nums[index + 1] && nums[index] < target) {
                index++;
            }
            if (nums[index] == target)
                ans = index;
        }
        if (target <= nums[nums.length - 1]) {
            index = nums.length - 1;
            while (index > 0 && nums[index] >= nums[index - 1] && nums[index] > target) {
                index--;
            }
            if (nums[index] == target)
                ans = index;
        }
        return ans;
    }

    @Test
    public void test() {
        Assertions.assertEquals(-1, searchBinarySimplify(new int[]{5,1, 3}, 0));
        Assertions.assertEquals(-1, searchBinarySimplify(new int[]{1, 3}, 0));
        Assertions.assertEquals(-1, searchBinarySimplify(new int[]{0}, 1));
        Assertions.assertEquals( 4, searchBinarySimplify(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
        Assertions.assertEquals(-1, searchBinarySimplify(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));
    }
}
