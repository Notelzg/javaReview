package ListAanTree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SortList {

    /**
     * 归并排序的思想,
     * 根据题目的要求时间复杂度是 O (n log N), 空间复杂度是 O(1)
     * 时间复杂度 O(n log n)的排序算法只有 归并和快排以及堆排序,快排每次都
     * 需要遍历元素显不适合,那只有归并排序,归并排序的问题在于快速找到中间节点
     * 所以这里使用slow fast思想来处理,slow指针一次位移一个节点,fast指针
     * 一次移动两个节点,当fast指针移到链表终点时,slow正好到中点
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        fast = slow.next;
        //隔断链表
        slow.next = null;
        ListNode left = sortList(head);
        ListNode right = sortList(fast);
        return  merger(left, right);
    }

    private ListNode merger(ListNode left, ListNode right) {
        if (left == null)
            return right;
        if (right == null)
            return left;
        ListNode head;
        ListNode cur;
        if (left.val > right.val) {
            head = right;
            right = right.next;
        } else {
            head = left;
            left = left.next;
        }
        cur = head;
        while (left != null && right != null) {
            if (left.val < right.val) {
                cur.next = left;
                left = left.next;
            } else {
                cur.next = right;
                right = right.next;
            }
            cur = cur.next;
        }
        cur.next = (left != null) ? left : right;
        return head;
    }

    /**
     * 在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序。
     * 因为使用插入排序 复杂度是 ,最坏情况是 O(n^2),
     * 但是使用头尾指针，可以避免u最坏情况，降低时间复杂度，如果
     * 去掉最坏情况之后，降低为  O（n ^ 2), 暴力解法 ,时间复杂度不够
     */
    public ListNode sortListInsert(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode cur = head.next;
        ListNode find = head;
        ListNode tail = head;
        ListNode next = null;
        head.next = null;
        while (cur != null) {
            next = cur.next;
            if (cur.val >= tail.val) {
                //尾插法
                cur.next = null;
                tail.next = cur;
                tail = cur;
                cur = next;
                continue;
            }
            if (cur.val <= head.val) {
                //头插法
                next = cur.next;
                cur.next = head;
                head = cur;
                cur = next;
                continue;
            }
            //从前向后找
            find = head;
            while (find.next != tail && cur.val > find.next.val) {
                find = find.next;
            }
            cur.next = find.next;
            find.next = cur;
            cur = next;
        }
        return head;
    }

    static class MyThread extends Thread{
        private String name;

        public MyThread(String name){
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("name:"+name+" 子线程ID:"+Thread.currentThread().getId());
        }
    }

    public static void main(String[] args)  {
        System.out.println("主线程ID:"+Thread.currentThread().getId());
        MyThread thread1 = new MyThread("thread1");
        thread1.start();
        MyThread thread2 = new MyThread("thread2");
        thread2.run();
    }
    @Test
    public void test() {
        Assertions.assertArrayEquals(new Integer[]{1, 2, 3, 4}, utils.list2arr(sortList(utils.arr2list(4, 2, 1, 3))));
        Assertions.assertArrayEquals(new Integer[]{-1, 0, 3, 4, 5}, utils.list2arr(sortList(utils.arr2list(-1, 5, 3, 4, 0))));
    }
}
