package ListAanTree;

import java.util.ArrayList;
import java.util.List;

public class utils {
    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print("--> " + head.val);
            head = head.next;
        }
        System.out.println();
    }
    public static  ListNode arr2list(int[] arr) {
        ListNode head = new ListNode(-1);
        ListNode listNode = head;
        for (int i = 0; i < arr.length; i++) {
            listNode.next = new ListNode(arr[i]);
            listNode = listNode.next;
        }
        return head.next;
    }
    public static  Integer[] list2arr(ListNode head) {
       List<Integer> ans = new ArrayList<>();
        if (head == null )
            return ans.toArray(new Integer[ans.size()]);
        while (head != null ){
            ans.add(head.val);
            head = head.next;
        }
        return ans.toArray(new Integer[ans.size()]);
    }
    public static boolean equal(ListNode h1, ListNode h2){
        if (h1 == null || h2 == null)
            return false;
        while (h1 != null && h2 != null){
           if (h1.val != h2.val)
               return false;
           h1 = h1.next;
           h2 = h2.next;
        }
        if (h1 != null || h2 != null)
            return false;
        return true;
    }
}
