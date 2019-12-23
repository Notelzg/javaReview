package ArrayAndSorted;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.parser.Entity;
import java.util.*;
import java.util.stream.IntStream;

/**
 * 思路，寻找水面积，其实就是两个拐点之间的面积减去拐点中间的节点面积
 * 难在寻找拐点，就是从升序转为降序的点，或者从降序转为升序再降序
 * 因为两个拐点相同点就是，从升序转为降序，不通点是第二种情况需要进行先进行降序
 * 那通过先降序转为升序的拐点，再从这个拐点开始进行 升序到降序的拐点，
 * left = 是从升序转为降序的点，
 * right = 或者从降序转为升序再降序的点
 * area = (right -left) * Math.min(left, right) - sum(left, right)
 */
public class GatherRain {
    public int down2up(int[] nums, int start) {
        int len = nums.length;
        //最少两个节点 才存在拐点
        if (start > len - 2)
            return -1;
        //降序，非升序，可能存在等于的情况
        int i = start;
        while (i < len - 1 && nums[i] >= nums[i + 1])
            i++;
        //i是最后一个元素 表明没有拐点
        if (i == len - 1)
            return -1;
        return i;
    }

    /**
     * 从一个点开始找升序到降序的拐点，如果不存在则返回-1
     *
     * @param nums
     * @param start
     * @return
     */
    public int up2down(int[] nums, int start) {
        int len = nums.length;
        //最少两个节点 才存在拐点
        if (start > len - 2)
            return -1;
        //升序,非降序
        int i = start;
        while (i < len - 1 && nums[i] <= nums[i + 1])
            i++;
        return i;
    }

    public int down2up2down(int[] nums, int start) {
        int len = nums.length;
        if (start < 0 || start > len - 3)
            return -1;
        int t = down2up(nums, start);
        if (-1 == t)
            return -1;
        t = up2down(nums, t);
        return t;
    }

    /**
     * 使用一个 prePoint 记录前一个拐点大于后一个拐点的
     * 档prePiont < currentPoint < nextPoint 则面积计算应该
     * 计算prPoint --> nextPoint，如果计算 prePoint -currentPoint 则不是最大面积
     * 从一个拐点到下一个拐点的距离，减去中间的节点
     * 如果下一个拐点大于前一个拐点,一直向前找直到找前一个拐点大的点，
     *
     * @param height
     * @return
     */
    public int trapGuaiDian(int[] height) {
        int len = height.length;
        //最少3个
        if (len < 3)
            return 0;
        int curPoint = 0;
        int prePoint;
        curPoint = up2down(height, curPoint);
        boolean[] flag = new boolean[height.length];
        while (curPoint != -1 && curPoint < len) {
            flag[curPoint] = true;
            prePoint = curPoint;
            curPoint = down2up2down(height, curPoint);
            if (-1 == curPoint)
                break;
            //当前节点比前一个节点大，所以需要找一个大于前一个拐点的拐点，如果没有则继续使用前一个拐点
            //使用一个栈存储最高点，就可以时间复杂度加起来就是  O(n)
            if (height[curPoint] > height[prePoint]) {
                for (int j = prePoint; j >= 0; j--){
                    if (!flag[j])
                        continue;
                    if (flag[j] && height[prePoint] < height[j])
                        prePoint = j;
                    if (height[curPoint] <= height[j])
                        break;
                }
                Arrays.fill(flag, prePoint + 1, curPoint, false);
            }
        }
        int minHeight;
        int area = 0;
        curPoint = 0;
        while (!flag[curPoint])
            curPoint++;
        prePoint = curPoint;
        for (int i = curPoint + 1; i < flag.length; i++){
            if (flag[i]) {
                minHeight = Math.min(height[prePoint], height[i]);
                area += (i - prePoint - 1) * minHeight;
                for (int j = prePoint + 1; j < i; j++)
                    area -= ((height[j] > minHeight) ? minHeight : height[j]);
                prePoint = i;
            }
        }
        return area;
    }

    /**
     * 思路，通过拐点计算方式，重新审题发现，其实不用找拐点，直接用
     * 当前节点左边的最大值 右边最大值，中小的那个减去当前节点就是接雨水的最大值
     * 之前想多了
     */
    public int trapArrayMax(int[] height){
        int len = height.length;
        if (len < 3)
            return 0;
        int[] leftMax = new int[len];
        int[] rightMax = new int[len];
        int ans = 0;
        //获取当前节点左边的最大值
        leftMax[0] = height[0];
        for (int i = 1; i < len; i++){
           leftMax[i]  = Math.max(height[i], leftMax[i-1]);
        }
        //获取当前节点右边的最大值
        rightMax[len - 1] = height[len - 1];
        for (int i = len - 2; i >= 0; i--){
            rightMax[i]  = Math.max(height[i], rightMax[i+1]);
        }
        for (int i = 1; i < len -1; i++){
           ans += (Math.min(leftMax[i], rightMax[i]))  - height[i];
        }
        return ans;
    }

    /**
     * 可以不用数组，使用栈的解法, 其实和我最初的想法是不一样的，但是我用了最笨的方法
     * 找最大左边值 和 右边值,求面积和，再减去中间节点，其实减只需要减一次就够了
     * 可以每次找到大值之后，使用累加法，计算大的面积就可以了.因为我最初的想法
     * 是找的拐点所以只能一个个去减，但是如果是对于每个点都进行这样的操作就不需要
     * 一个个去减了， 只要每次找到大的值，减去就好了,
     * 相当于每次计算的area是当前最大高度的，但是醉着高度增加，把之前没有加上的高度
     * 通过增量法添加上来了
     * @param height
     * @return
     */
    public int trapAdd(int[] height){
        int len = height.length;
        int ans = 0;
        if (len < 3)
            return 0;
       Stack<Integer>  stack = new Stack<>();
       int left = 0;
       while (left < len){
           while (!stack.empty() &&  height[left] > height[stack.peek()]){
                int top = stack.peek();
                stack.pop();
                if (stack.isEmpty())
                    break;
                int bound = Math.min(height[left], height[stack.peek()])  - height[top];
                ans += bound * (left - stack.peek() - 1);
           }
           stack.push(left++);
       }
       return ans;
    }

    /**
     * 双指针法，当 height[current] 节点的可以累积的水量
     * 是由其左边的最大值和右边的最大值中小的那个值决定的，那我们之前的都是记录左边的最大值
     * 和右边的最大值，要么是用数组，要么是用栈，现在我们使用两个指针来解决。
     * 思路是：当height[left] > height[right]时，我们使用rightMax作为基准值计算积水量
     *         height[left] < height[right] 我们用leftMax作为基准值计算积水量
     *         height[left] = height[right] 相等时，leftMax 和 rightMax 都可以作为积水量
     *
     *         当right作为积水量时，需要更新right的最大值
     *
     * @param height
     * @return
     */
    public int trap(int[] height){
        int size = height.length;
        if (size < 3)
            return 0;
        int ans = 0;
        int leftMax = 0;
        int rightMax = 0;
        int left = 0;
        int right = size -1;
        //当left == left的时候，其实是  height[left] height[right]本身就是最高点了，无法积累雨水
        // 所以 left  < right
        while (left < right){
            // 大于 right，从rightMax进行计算
           if (height[left] > height[right]) {
              if (height[right] > rightMax)
                  rightMax = height[right];
              ans += rightMax - height[right];
              right--;
           }else {
              if (height[left] > leftMax)
                  leftMax = height[left];
              ans +=  leftMax - height[left];
              left++;
           }
        }
        return ans;
    }
    /**
     * 优化思路1， 只用两个点记录,左边最大值右边最大值
     */
    @Test
    public void test() {
        Assertions.assertEquals(39, trap(new int[]{1,9,7,1,3,6,4,7,4,8,3,6,3,5,3,7}));
        Assertions.assertEquals(6, trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        Assertions.assertEquals(14, trap(new int[]{5, 2, 1, 2, 1, 5}));
        Assertions.assertEquals(83, trap(new int[]{6, 4, 2, 0, 3, 2, 0, 3, 1, 4, 5, 3, 2, 7, 5, 3, 0, 1, 2, 1, 3, 4, 6, 8, 1, 3}));
        Assertions.assertEquals(0, trap(new int[]{0, 1, 1}));
        Assertions.assertEquals(0, trap(new int[]{0, 1}));
        Assertions.assertEquals(0, trap(new int[]{0, 1, 1, 2}));
        Assertions.assertEquals(0, trap(new int[]{0, 1, 1, 2, 1}));
        Assertions.assertEquals(1, trap(new int[]{0, 1, 1, 2, 1, 2}));
    }
}
