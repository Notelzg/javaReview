package level_easy;

import org.junit.jupiter.api.Test;

/**
 * 合并两个有序列表
 */
public class MergeSortedTwoLists {
    /**
     * 递归法
     * list = List1[0] + merge(list1[1..n], list2[0...n] list1[0] < list2[0]
     * list = List2[0] + merge(list1[0..n], list2[1...n]  otherwise
     */
    public ListNode mergeTwoListsRecursive(ListNode l1, ListNode l2) {
        if (null == l1) {
            return l2;
        } else if (null == l2) {
            return l1;
        } else if (l1.val < l2.val) {
            l1.next = mergeTwoListsRecursive(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoListsRecursive(l1, l2.next);
            return l2;
        }
    }

    /**
     * 迭代法
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        final ListNode head = new ListNode(-1);
        ListNode cur = head;
        while (null != l1 && null != l2) {
            if (l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        if (l1 != null)
            cur.next = l1;
        if (l2 != null)
            cur.next = l2;
        return head.next;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

}
