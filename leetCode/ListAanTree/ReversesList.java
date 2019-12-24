package ListAanTree;

import org.junit.jupiter.api.Test;

/**
 * 反转一个单链表
 * 思路1， 使用头插法完成反转，这个时迭代法
 * 思路2， 使用递归的方法，头插法
 */
public class ReversesList {

    /**
      思路2
      当head 为null，表明 head 时一个空链表
      当 next = head.next 不为空时进让， next.next = head , 同时 head.next =null, 去除循环
      其实递归很好理解， 就是 把 链表的 head->next, 变为 next->head, 因为递归会记录next.next所以不用担心 next.next后面的
      数据丢失
      不为空，表明  next 是链表最后一个元素，返回该元素
      时间复杂度 是链表的长度 O(n)
      空间复杂度  o(n) ,由于是递归使用栈空间，n层，每层使用一个ListNode,由于对象分配在堆中不会随着递归退出而释放
      所以空间复杂度时 O(2n) ==> O(n)
     *
     * @param head
     * @return
     */
    public ListNode reverseListRecursive(ListNode head) {
        if (head == null)
            return head;
        else if (head.next != null) {
            ListNode ans = reverseListRecursive(head.next);
            ListNode next = head.next;
            next.next = head;
            head.next = null;
            return ans;
        } else {
            return head;
        }
    }

    /**
     * 思路1
     * 使用一个节点记录，
     * 使用一个节点记录
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        if (head == null)
            return head;
        ListNode headNext = head.next;
        //设置为尾节点
        head.next = null;
        ListNode headNextNext;
        while (headNext != null) {
            headNextNext = headNext.next;
            headNext.next = head;
            head = headNext;
            headNext = headNextNext;
        }
        return head;
    }

    public static void prinList(ListNode head) {
        while (head != null) {
            System.out.print("--> " + head.val);
            head = head.next;
        }
        System.out.println();
    }

    @Test
    public void test() {
        ListNode head = new ListNode(0);
        ListNode tail = head;
        for (int i = 1; i < 5; i++) {
            tail.next = (new ListNode(i));
            tail = tail.next;
        }
        head = reverseListRecursive(head);
        head = null;
        reverseListRecursive(head);
    }

}
