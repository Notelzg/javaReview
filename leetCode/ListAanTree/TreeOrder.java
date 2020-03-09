package ListAanTree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.omg.PortableInterceptor.INACTIVE;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeOrder {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> rs = new ArrayList<>();
        preOrderRecursive(rs, root);
        return rs;
    }

    public void preOrderRecursive(List list, TreeNode root) {
        if (root == null)
            return;
        list.add(root.val);
        preOrderRecursive(list, root.left);
        preOrderRecursive(list, root.right);
    }

    public void postOrderRecursive(List list, TreeNode root) {
        if (root == null)
            return;
        postOrderRecursive(list, root.left);
        postOrderRecursive(list, root.right);
        list.add(root.val);
    }

    public void inOrderRecursive(List list, TreeNode root) {
        if (root == null)
            return;
        inOrderRecursive(list, root.left);
        list.add(root.val);
        inOrderRecursive(list, root.right);
    }

    List<List<Integer>> rs = new ArrayList<>();

    public List<List<Integer>> levelOrder1(TreeNode root) {
        levelOrderRecursive(root, 0);
        return rs;
    }

    public void levelOrderRecursive(TreeNode root, int level) {
        if (root == null)
            return;
        if (rs.size() == level)
            rs.add(new ArrayList<Integer>());
        rs.get(level).add(root.val);
        if (root.left != null)
            levelOrderRecursive(root.left, level + 1);
        if (root.right != null)
            levelOrderRecursive(root.right, level + 1);
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> rs = new ArrayList<>();
        if (root == null)
            return rs;
        queue.add(root);
        TreeNode temp;
        int level = 0;
        TreeNode pre = root;
        int queueLen;
        while (!queue.isEmpty()) {
            rs.add(new ArrayList<>());
            queueLen = queue.size();
            for (int i = 0; i < queueLen; i++) {
                temp = queue.poll();
                rs.get(level).add(temp.val);
                if (temp.left != null)
                    queue.add(temp.left);
                if (temp.right != null)
                    queue.add(temp.right);
            }
            level++;
        }
        return rs;
    }

    int maxDepth = 0;

    /**
     * 二叉树的最大深度
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        depthUpDown(root, 0);
        return maxDepth;
    }

    private int depthDownUp(TreeNode root) {
        if (root == null)
            return 0;
        int l = depthDownUp(root.left);
        int r = depthDownUp(root.right);
        return Math.max(l, r) + 1;
    }

    private void depthUpDown(TreeNode root, int depth) {
        if (root == null)
            return;
        if (root.left == null && root.right == null)
            maxDepth = Math.max(maxDepth, depth);
        depthUpDown(root.left, depth + 1);
        depthUpDown(root.right, depth + 1);
    }

    /**
     * 二叉树是否是 对称的，镜像对称
     * 层次遍历
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return false;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int queueLen;
        boolean rootFlag = true;
        TreeNode temp;
        ArrayList<Integer> list = new ArrayList<>();
        while (!queue.isEmpty()) {
            queueLen = queue.size();
            list.clear();
            for (int i = 0; i < queueLen; i++) {
                temp = queue.poll();
                if (temp == null) {
                    list.add(Integer.MIN_VALUE);
                    continue;
                }
                list.add(temp.val);
                if (temp.left != null)
                    queue.add(temp.left);
                else
                    queue.add(null);
                if (temp.right != null)
                    queue.add(temp.right);
                else
                    queue.add(null);
            }
            // check
            if (rootFlag) {
                rootFlag = false;
                continue;
            }
            if ((queueLen % 2) != 0)
                return false;
            for (int i = 0; i < queueLen / 2; i++) {
                if (!list.get(i).equals(list.get(queueLen - i - 1)))
                    return false;
            }
        }
        return true;
    }

    public boolean hasPathSum(TreeNode root, int sum) {
        return hasPathRecursive(root, sum, 0);
    }

    private boolean hasPathRecursive(TreeNode root, int sum, int parent) {
        if (root == null)
                return false;
        // is leaf
        if (root.left == null && root.right == null)
            if (parent + root.val == sum)
                return true;
            else
                return false;
        boolean l = hasPathRecursive(root.left , sum, parent + root.val);
        boolean r = hasPathRecursive(root.right, sum, parent + root.val);
        return l | r;
    }

    @Test
    public void testPathSum() {
        Assertions.assertEquals(true, hasPathSum(utils.arr2tree(), 0));
        Assertions.assertEquals(true, hasPathSum(utils.arr2tree(5,4,8,11,null,13,4,7,2,null,null,null,1), 22));
    }

    @Test
    public void testDynamicc() {
        Assertions.assertEquals(true, isSymmetric(utils.arr2tree(utils.str2IntArr("1,2,2,3,4,4,3"))));
        Assertions.assertEquals(false, isSymmetric(utils.arr2tree(utils.str2IntArr("[1,2,2,0,3,0,3]"))));
    }
}
