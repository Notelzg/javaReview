package ListAanTree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *给定一个二叉树，返回其节点值的锯齿形层次遍历。（即先从左往右，
 * 再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
  思路1，顺着题目来，首先是层次遍历需要使用队列，
   层次遍历默认是 从左到右，但是为了形成之字形，我们可以使用一个 标记
   如果上一次是从左到右，则本次就是从右到走，我们只需要把list进行reverse就可以
   了，层次遍历不需要改，只是每次在层次结束得时候，看是否需要翻转list即可。
 */
public class TreeZigzagLevelOrder {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (null == root)
            return ans;
        //因为根节点先入队，是左边访问
        boolean left = true;
        LinkedList<TreeNode> queen = new LinkedList<>();
        queen.add(root);
        queen.add(null);
        List<Integer> item = new ArrayList<>();
        TreeNode first;
        while (!queen.isEmpty()){
            first = queen.pollFirst();
            //使用null 来标记一层得结束
            if (first == null ) {
               if (!queen.isEmpty()) queen.add(null);
               if (!left) Collections.reverse(item);
               ans.add(item);
               item = new ArrayList<>();
               left = !left;
               continue;
            }
            item.add(first.val);
            if (first.left != null)
                   queen.add(first.left);
            if (first.right != null)
                queen.add(first.right);
        }
        return ans;
    }

    @Test
    public void test(){
        Assertions.assertArrayEquals(new Integer[]{3}, zigzagLevelOrder(utils.arr2tree(3)).toArray());
        TreeNode root = utils.arr2tree(3,9,20,null,null,15,7);
        Integer[][] an = new Integer[][]{{3}, {20,9}, {15,7}};
        int i = 0;
        for (List<Integer> integers : zigzagLevelOrder(root)) {
            Assertions.assertArrayEquals(integers.toArray(new Integer[integers.size()]), an[i++]);
        }

//         3
//        / \
//        9  20
//          /  \
//        15   7
//        返回锯齿形层次遍历如下：
//[
//  [3],
//  [20,9],
//  [15,7]
//]
    }
}
