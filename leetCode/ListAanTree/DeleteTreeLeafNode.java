package ListAanTree;

import com.sun.org.apache.xpath.internal.objects.XNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 删除树的叶子节点
 */
public class DeleteTreeLeafNode {
    /**
     * 思路，分治思想
     * 如果一个节点p的左子树是叶子节点则把p节点的 left置为null
     * 如果一个节点p的右子树是叶子节点则把 p.right = null;
     */
    public TreeNode delete(TreeNode root){
        if (root == null)
            return root;
        deleteCircle(root);
        return root;
    }
    public boolean deleteLeafRecursive(TreeNode root){
        if (root == null)
            return false;
        //判断是叶子节点
        if (root != null && root.left == null && root.right == null)
            return true;
        boolean l = deleteLeafRecursive(root.left);
        boolean r = deleteLeafRecursive(root.right);
        if (l) {
            root.left = null;
        }
        if (r)
            root.right = null;
        return false;
    }

    /**
     * 采用后续遍历，判断孩子节点是否是叶子节点，是则加入相应的列表
     * 遍历列表，设置孩子节点为空
     * @param root
     */
    public void deleteCircle2(TreeNode root){
        List<TreeNode> lList = new ArrayList<>();
        List<TreeNode> rList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode left, right,parent;
        boolean lf = true;
        TreeNode pre = null;
        while (root != null || !stack.isEmpty()){
            while (root != null){
                stack.push(root);
                root = root.left;
            }
            if (stack.isEmpty())
                continue;
            root = stack.peek();
            right = root.right;
            if (right == null || right == pre){
               root  = stack.pop();
                if (isLeaf(root.left))
                    lList.add(root);
                if (isLeaf(root.right))
                    rList.add(root);
                pre = root;
                root = null;
            }else {
                root = right;
            }
        }
        for (TreeNode treeNode : lList) {
           treeNode.left = null;
        }
        for (TreeNode treeNode : rList) {
            treeNode.right = null;
        }
    }

    /**
     *
     * @param root
     */
    public void deleteCircle(TreeNode root){
        List<TreeNode> lList = new ArrayList<>();
        List<TreeNode> rList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode left, right,parent;
        boolean lf = true;
        TreeNode pre = null;
        while (root != null || !stack.isEmpty()){
            while (root != null){
                stack.push(root);
                root = root.left;
            }
            if (stack.isEmpty())
                continue;
            root = stack.peek();
            right = root.right;
            if (right == null || right == pre){
                root  = stack.pop();
                if (isLeaf(root.left))
                    lList.add(root);
                if (isLeaf(root.right))
                    rList.add(root);
                pre = root;
                root = null;
            }else {
                root = right;
            }
        }
        for (TreeNode treeNode : lList) {
            treeNode.left = null;
        }
        for (TreeNode treeNode : rList) {
            treeNode.right = null;
        }
    }
    public boolean isLeaf(TreeNode root){
        if (root != null && root.left == null && root.right == null ){
            return true;
        }
        return false;
    }

    @Test
    public void test(){
        utils.printTree(delete(utils.arr2tree(1,2,3)));
        System.out.println();
        utils.printTree(delete(utils.arr2tree(1,2,3, 4, 5)));
    }
}
