package ListAanTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class utils {
    public static Integer[] str2intArr(String str){
        Objects.requireNonNull(str);
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);
        List<Integer> ans = new ArrayList<>();
        while (matcher.find())
            ans.add(Integer.parseInt(matcher.group()));
        return ans.toArray(new Integer[ans.size()]);

    }
    public static int[] str2intArrArr(String str){
        Pattern pattern = Pattern.compile("\\[.*]");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()){
            System.out.println(matcher.start());
            System.out.println(matcher.end());
            System.out.println(matcher.group());
            String str2 =  str.substring(matcher.start() + 1, matcher.end() -1);
            Matcher matcher2 = pattern.matcher(str2);
            while (matcher2.find()) {
                System.out.println(matcher2.start());
                System.out.println(matcher2.end());
                System.out.println(matcher2.group());
            }

        }
        return null;
    }
    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print("--> " + head.val);
            head = head.next;
        }
        System.out.println();
    }
    public static  ListNode arr2listTail(int[] arr, int index) {
        ListNode head = new ListNode(-1);
        ListNode listNode = head;
        ListNode circle  = null;
        for (int i = 0; i < arr.length; i++) {
            listNode.next = new ListNode(arr[i]);
            listNode = listNode.next;
            if (i == index)
               circle =  listNode;
        }
        listNode.next = circle;
        return head.next;
    }
    public static  TreeNode arr2tree(Integer... arr) {
        return createChild(arr, 0);
    }
    private static TreeNode createChild(Integer[] arr, int parentIndex){
        TreeNode parent = new TreeNode(arr[parentIndex]);
        int left = parentIndex * 2 + 1;
        if (left < arr.length)
            parent.left =  createChild(arr, left);
        if (left  + 1 < arr.length)
            parent.right =  createChild(arr, left + 1);
        return parent;
    }

    public static  ListNode arr2list(Integer... arr) {
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
