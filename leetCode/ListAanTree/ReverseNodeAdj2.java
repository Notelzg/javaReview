package ListAanTree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * 滴滴一面面试题
 * 把单链表 每两个节点进行反转，如果节点个数是奇数个，最后一个不反转.
 * 例子1，
 * 输入：
 * 1-->2-->3-->4
 * 输出
 * 2-->1-->4--->3
 * 例子2
 * 输入：
 * 1-->2-->3
 * 输出
 * 2-->1-->3
 */
public class ReverseNodeAdj2 {

    public ListNode reverse(ListNode root){
        if (root == null || root.next == null)
            return root;
        ListNode pre = root;
        ListNode next = root.next;
        ListNode head = new ListNode(0);
        //记录一个前节点，使反转之后的链表和已经反转的链表链接到一块
        ListNode prepre = head;
        while (next != null){
            pre.next = next.next;
            next.next = pre;
            prepre.next = next;
            prepre = pre;
            if (pre.next == null)
                break;
            pre = pre.next;
            next = pre.next;
        }
        return head.next;
    }

    @Test
    public void test(){
        String str = "1,2,3,4";
        String str2 = "2,1,4,3";
        Integer[] ia = utils.str2IntArr(str);
        Assertions.assertArrayEquals(utils.str2IntArr(str2), utils.list2arr(reverse(utils.arr2list(ia))));
        str = "1,2,3";
        str2 = "2,1,3";
        ia = utils.str2IntArr(str);
        Assertions.assertArrayEquals(utils.str2IntArr(str2), utils.list2arr(reverse(utils.arr2list(ia))));
        str = "1,2";
        str2 = "2,1";
        ia = utils.str2IntArr(str);
        Assertions.assertArrayEquals(utils.str2IntArr(str2), utils.list2arr(reverse(utils.arr2list(ia))));
        str = "";
        str2 = "";
        ia = utils.str2IntArr(str);
        Assertions.assertArrayEquals(utils.str2IntArr(str2), utils.list2arr(reverse(utils.arr2list(ia))));
        str = "1";
        str2 = "1";
        ia = utils.str2IntArr(str);
        Assertions.assertArrayEquals(utils.str2IntArr(str2), utils.list2arr(utils.arr2list(ia)));
    }
}
