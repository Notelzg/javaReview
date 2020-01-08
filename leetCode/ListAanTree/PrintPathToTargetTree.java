package ListAanTree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 一个二插树，给你一个根节点和一个目标节点，输出从根节点到目标节点的路径。
 * 思路，使用递归的方式
 * 如果当前结果和目标节点一样，则返回true，并且加入路径中。
 * 如果左孩子节点返回true，则把当前节点加入路径中。
 * 如果右孩子节点返回true，则把当前节点加入路径中。
 * 只要孩子节点返回true，则返回true.
 */
public class PrintPathToTargetTree {
    /**
     *
     * @param root
     * @param target
     * @return
     */
    public String printPath(TreeNode root, TreeNode target){
       if (root == null || target == null)
           return "";
       StringBuilder sb = new StringBuilder();
       print(root, target, sb);
       return  sb.reverse().toString();
    }
    private boolean print(TreeNode root, TreeNode target, StringBuilder sb){
        if (root == null || target == null)
            return false;
        if (root.val == target.val){
            sb.append(root.val);
            return true;
        }
        boolean l = print(root.left, target, sb);
        if (l) {
            sb.append("," + root.val);
            return true;
        }
        boolean r = print(root.right, target, sb);
        if (r) {
            sb.append("," + root.val);
            return true;
        }
        return false;
    }
    @Test
    public void test(){
        String tree = "1,2,3,4,5";
        String target = "5";
        String path = "1,2,5";
        Assertions.assertEquals(path, printPath(utils.arr2tree(utils.str2IntArr(tree)), new TreeNode(5)));
    }

}
