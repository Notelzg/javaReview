package ListAanTree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.nimbus.AbstractRegionPainter;

/**
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 * <p>
 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，
 * 最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
 * 说明:
 * 所有节点的值都是唯一的。
 * p、q 为不同节点且均存在于给定的二叉树中。
 * 思路1， 使用后续遍历二叉树，如果p q已经被访问过， 则当前节点就是公共祖先
 */
public class FindLowestCommonAncestor {
    private static boolean fp = false;
    private static boolean fq = false;
    private TreeNode p;
    private TreeNode q;
    private TreeNode ans = null;

    private boolean postOrder(TreeNode root) {
        if (null == root)
            return false;
        boolean left = postOrder(root.left);
        boolean right = postOrder(root.right);
        if (root == p || root == q) {
            if (left)
                right = true;
            else
                left = true;
        }
        ;
        if (left && right) {
            ans = root;
            return false;
        }
        return left | right;
    }

    /**
     * 思路1
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null)
            return null;
        if (q == null)
            return null;
        if (p == null)
            return null;
        this.p = p;
        this.q = q;
        postOrder(root);
        return ans;
    }

    @Test
    public void test() {
        String u = null + "," + "13";
        String[] t = u.split(",");
        /**
         * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
         * 输出: 3
         * 解释: 节点 5 和节点 1 的最近公共祖先是节点 3。
         * 示例 2:
         *
         * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
         * 输出: 5
         * 解释: 节点 5 和节点 4 的最近公共祖先是节点 5。因为根据定义最近公共祖先节点可以为节点本身。
         */
        TreeNode root = utils.arr2tree(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4);
        TreeNode root1 = utils.arr2tree(3);
        TreeNode root2 = utils.arr2tree(3, 5);
        TreeNode root3 = utils.arr2tree(3, 5, null, 6);
        Assertions.assertEquals(null, lowestCommonAncestor(root1, getNode(root1, 3), null));
        Assertions.assertEquals(3, lowestCommonAncestor(root2, getNode(root2, 5), getNode(root2, 3)).val);
        Assertions.assertEquals(5, lowestCommonAncestor(root3, getNode(root3, 5), getNode(root3, 6)).val);
        Assertions.assertEquals(5, lowestCommonAncestor(root, getNode(root, 5), getNode(root, 6)).val);
        Assertions.assertEquals(2, lowestCommonAncestor(root, getNode(root, 7), getNode(root, 4)).val);
        Assertions.assertEquals(5, lowestCommonAncestor(root, getNode(root, 6), getNode(root, 5)).val);
        Assertions.assertEquals(3, lowestCommonAncestor(root, getNode(root, 5), getNode(root, 1)).val);
        Assertions.assertEquals(5, lowestCommonAncestor(root, getNode(root, 5), getNode(root, 4)).val);
    }

    public static TreeNode getNode(TreeNode root, int val) {
        if (root == null)
            return null;
        TreeNode ans = null;
        if (root.val == val)
            return root;
        ans = getNode(root.left, val);
        if (ans != null)
            return ans;
        ans = getNode(root.right, val);
        if (ans != null)
            return ans;
        return null;
    }
}
