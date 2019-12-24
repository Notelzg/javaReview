package ListAanTree;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * <p>
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * <p>
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * 思路1：
 * 遍历两个链表取出 num1 和 num2，
 * 把num1+num2 结果转为字符串，然后转为链表返回
 * 也可以通过 除以10，转为链表
 * 思路2，一遍遍历
 * 因为是逆序存储 最小得值存在开头，所以直接进行加法，如果大于9，则作为一个extra值，放到下一次循环中继续进行加
 */
public class AddTwoNumers {
    private int add(int n1, int n2, int extra, ListNode node) {
        int sum = n1 + n2 + extra;
        extra = sum / 10;
        sum %= 10;
        node.val = sum;
        return extra;
    }
    /**
     * 步骤
     * 第一步，把两个链表所有值相加
     * 第二步，哪个不为空继续进行处理
     * 第三部，如果extra > 0, 新建一个节点
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;
        final ListNode head = l1;
        int extra = 0;
        int sum;
        ListNode preL1 = l1;
        ListNode cur = l1;
        boolean flag = true;
        int num1 = 0;
        int num2 = 0;
        while (l1 != null ||  l2 != null) {
            if (l1 == null && flag) {
                preL1.next = l2;
            }
            preL1 = (l1 ==null) ? l2 : l1;
            num1 = (l1 == null) ? 0 : l1.val;
            num2 = (l2 == null) ? 0 : l2.val;
            sum = num1 + num2 + extra;
            extra = sum / 10;
            sum %= 10;
            l1.val = sum;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        if (extra > 0)
            preL1.next = new ListNode(extra);
        return head;
    }
        /**
         * 步骤
         * 第一步，把两个链表相同的位数相加
         * 第二步，哪个不为空继续进行处理
         * 第三部，如果extra > 0, 新建一个节点
         * @param l1
         * @param l2
         * @return
         */
    public ListNode addTwoNumbers5(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;
        final ListNode head = l1;
        int extra = 0;
        int sum;
        ListNode preL1 = l1;
        while (l1 != null && l2 != null) {
            preL1 = l1;
            extra = add(l1.val, l2.val, extra, l1);
            l1 = l1.next;
            l2 = l2.next;
        }

        while (l1 != null) {
            preL1 = l1;
            sum = l1.val + extra;
            extra = add(l1.val, 0, extra, l1);
            if (sum < 10)
                break;
            l1 = l1.next;
        }
        if (l2 != null)
            preL1.next = l2;
        while (l2 != null) {
            preL1 = l2;
            sum = l2.val + extra;
            extra = add(l2.val, 0, extra, l2);
            if (sum < 10)
                break;
            l2 = l2.next;
        }
        if (extra != 0 && l1 == null && l2 == null) {
            preL1.next = new ListNode(extra);
            return head;
        }
        return head;
    }

    private String list2String(ListNode listNode) {
        StringBuilder sb = new StringBuilder();
        while (listNode != null) {
            sb.append(listNode.val);
            listNode = listNode.next;
        }
        return sb.toString();
    }

    private int list2num(ListNode listNode) {
        int ans = 0;
        while (listNode != null) {
            ans += (ans * 10) + listNode.val;
            listNode = listNode.next;
        }
        return ans;
    }

    /**
     * 这个题目没有看清，算法计算得是正序存储得链表
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;
        int num1, num2;
        String s1 = list2String(l1);
        String s2 = list2String(l2);
        num1 = Integer.parseInt(s1);
        num2 = Integer.parseInt(s2);
        num1 = num2 + num1;
        ListNode head = null;
        ListNode temp;
        while (num1 / 10 != 0) {
            temp = new ListNode(num1 % 10);
            temp.next = head;
            head = temp;
            num1 = num1 / 10;
        }
        temp = new ListNode(num1);
        temp.next = head;
        return temp;
    }

    @Test
    public void test() {
        ListNode head1 = new ListNode(-1);
        ListNode head2 = new ListNode(-1);
        addTwoNumbers(utils.arr2list(8, 6), utils.arr2list(6,4, 8));
    }


}
