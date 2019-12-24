package ListAanTree;

import org.junit.jupiter.api.Test;

/**
 * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * 思路：
 * 插入排序，如果 l1.val > l2.val, 把l1 插入，并且后移动一位
 */
public class MergeTwoList {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        final ListNode head = new ListNode(-1);
        ListNode tail = head;
        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                tail.next = l2;
                l2 = l2.next;
            } else {
                tail.next = l1;
                l1 = l1.next;
            }
            tail = tail.next;
        }
        if (l1 != null)
            tail.next = l1;
        if (l2 != null)
            tail.next = l2;
        return head.next;
    }
    @Test
    public void test() {

    }
}
