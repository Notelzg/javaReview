package ListAanTree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * 编写一个程序，找到两个单链表相交的起始节点。
 * 如果两个链表没有交点，返回 null.
 * 在返回结果后，两个链表仍须保持原有的结构。
 * 可假定整个链表结构中没有循环。
 * 程序尽量满足 O(n) 时间复杂度，且仅用 O(1) 内存。
 *
 * 思路1
 * 暴力解决,遍历结果存储到list中,然后从尾到头进行循环,直到
 * 找打不一样的地方,其下一个节点就是相交节点
 *  但是内存使用是  O(n)
 *  思路2
 *  如果链表a,b长度一样,则我们只要从前向后进行判断节点是否相等就行了,如果相等则是相交节点
 *  因为a b长度不一致,但是 a+b = b +a , 所以我们如果把 a 链接到b的后面, 把 b链接到a的后面
 *  得到两个新的链表长度是一样的,这样就解决了长度不一致的问题
 *
 */
public class FindIntersection {
    public ListNode getIntersection(ListNode headA, ListNode headB) {
        if (null == headA || null == headB)
            return null;
        ListNode p1 = headA;
        ListNode p2 = headB;
        //如果 p1 和 p2长度相等,则最后一次都是null,则返回null
        //如果 p1 不等于 p2, 则 p1 和 p2不可能同时达到终点,就不会同时等于null,除了链接之后达到终点null,结束循环
        while (p1 != p2){
            if (p1 != null)
                p1 = p1.next;
            else
                p1 =headB;

            if (p2 != null)
                p2 = p2.next;
            else
                p2 = headA;
        }
        return p1;
    }

    public ListNode getIntersectionNodeHash(ListNode headA, ListNode headB) {
        if (null == headA || null == headB)
            return null;
        HashSet set = new HashSet();
        while (headA != null){
            set.add(headA);
            headA = headA.next;
        }
        while (headB != null){
            if (set.contains(headB))
               return headB;
            headB = headB.next;
        }
        return null;
    }

    @Test
    public void test(){
        String str1 = "[[4,1,8,4,5]]";
        ListNode a = utils.arr2list(new Integer[]{4});
        ListNode b = utils.arr2list(new Integer[]{5});
        Assertions.assertEquals(8, getIntersection(a, b).val);
    }
}
