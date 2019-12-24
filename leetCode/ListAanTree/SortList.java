package ListAanTree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序。
 * 因为使用插入排序 复杂度是 ,最坏情况是 O(n^2),
 * 但是使用头尾指针，可以避免u最坏情况，降低时间复杂度，如果
 * 去掉最坏情况之后，降低为  O（n long N)
 */
public class SortList {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null)
                    return head;
                ListNode cur = head.next;
                ListNode find = head;
                ListNode  tail = head;
                ListNode next = null;
                head.next = null;
                while (cur != null){
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
            while (find.next != tail &&  cur.val > find.next.val){
                find = find.next;
            }
            cur.next = find.next;
            find.next = cur;
            cur = next;
        }
        return head;
    }

    @Test
    public void test(){
        Assertions.assertArrayEquals(new Integer[]{1,2,3,4}, utils.list2arr(sortList(utils.arr2list(new int[]{4,2,1,3}))));
        Assertions.assertArrayEquals(new Integer[]{-1, 0, 3, 4, 5}, utils.list2arr(sortList(utils.arr2list(new int[]{-1,5,3,4,0}))));
    }
}
