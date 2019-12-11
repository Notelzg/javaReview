package level_easy;

import org.junit.jupiter.api.Test;

/**
 * 反转一个单链表。
 *
 * 示例:
 *
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 */

public class ReverseList {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }

        @Override
        public String toString() {
            return "" + val;

        }
    }

    public ListNode reverseList(ListNode head) {
        if (null == head || null == head.next)
            return head;
        /* addAtHead*/
        ListNode reverseHead  = new ListNode(head.val);
        ListNode temp;
        while (head.next != null){
            temp = new ListNode(head.next.val);
            temp.next = reverseHead;
            reverseHead = temp;
            head = head.next;
        }
       return  reverseHead;
    }

    public ListNode reverseListO1(ListNode head) {
        if (null == head || null == head.next)
            return head;
        /* addAtHead*/
        ListNode cur  = head.next;
        head.next = null;
        ListNode temp;
        while (cur.next != null){
            temp = cur;
            cur = cur.next;
            temp.next = head;
            head = temp;
        }
        cur.next = head;
        return  cur;
    }

    @Test
    public void test(){
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        ListNode rs = reverseListO1(listNode1);
        while (rs != null && rs.next != null) {
            System.out.print(rs + "-->");
            rs = rs.next;
        }
        System.out.println();
    }
}
