package ListAanTree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
 *
 * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
 *
 * 说明：不允许修改给定的链表。
 */
public class CircleListII {
    /**
     * 使用数学解法 解决环的问题
     * 从头结点到环节点距离为 a(不包含环入口), 环的长度为 b
     * 还是使用 slow fast快慢指针(slow步长一个节点,fast步长两个节点), 因为如果存在环则 slow fast
     * 一定相遇,如果不存在环则fast为空
     * 当slow fast 相遇的时候的 slow 步数 s, fast步长f
     * f = 2s, 公式1
     *  slow fast相遇的时候,fast比slow多跑了n圈, f = s + nb 公式2
     * 公式1-公式2 , 0 = s - nb  , s = nb
     * k = a + nb, 表明k节点在环的入口, 所以s走a步之后在入口,需要返回该节点
     * 但是a b 未知,但是如果有一个节点从头结点出发,当和 slow相遇的时候
     * 就是换的入口
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null)
            return null;
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        while (fast != null && fast.next != null && slow != fast){
            fast =fast.next.next;
            slow = slow.next;
        }

        if (fast == null || fast.next == null)
            return null;
        fast = head;
        while (fast != slow){
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * hash存储访问过的节点,如果碰见节点,则证明存在环,返回节点
     * 其实这里有个问题,如果已经存在环,则链表就已经到结尾了
     * 因为存在环的话,即使环后面还有节点,也无法访问.
     * @param head
     * @return
     */
    public ListNode detectCycleHashSet(ListNode head) {
        if (head == null || head.next == null)
            return null;
        HashSet<ListNode> hash = new HashSet<>();
        while (head!= null){
            if (hash.contains(head))
                return head;
            hash.add(head);
            hash.forEach((key)-> System.out.print(key  + ", "));
            System.out.println();
            head = head.next;
        }
        return null;
    }

    @Test
    public void  test(){
        Assertions.assertEquals(2, detectCycle(utils.arr2listTail(new int[]{3,2,0,-4}, 1)).val);
        Assertions.assertEquals(-9, detectCycle(utils.arr2listTail(new int[]{-1,-7,7,-4,19,6,-9,-5,-2,-5}, 6)).val);
        Assertions.assertEquals(null, detectCycle(utils.arr2listTail(new int[]{3,2,0,-4}, -1)));
        Assertions.assertEquals(1, detectCycle(utils.arr2listTail(new int[]{1,2,1}, 0)).val);
        Assertions.assertEquals(null, detectCycle(utils.arr2listTail(new int[]{1}, -1)));
    }
}
