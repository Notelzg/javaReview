package ListAanTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class utils {
    public static int[] str2intArr(String str){
        Integer[] arr = str2IntArr(str);
        int[] ans = new int[arr.length];
        for (int i = 0; i < ans.length; i++)
            ans[i] = arr[i];
        arr = null;
        return  ans;
    }
    public static Integer[] str2IntArr(String str){
        Objects.requireNonNull(str);
        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(str);
        List<Integer> ans = new ArrayList<>();
        while (matcher.find())
            ans.add(Integer.parseInt(matcher.group()));
        return ans.toArray(new Integer[ans.size()]);

    }
    public static int[][] str2intArrArr(String str){
        Pattern pattern = Pattern.compile("\\[.*]");
        Matcher matcher = pattern.matcher(str);
        List<List<Integer>> list = new ArrayList<>();
        while (matcher.find()){
            String[] str2 =  str.substring(matcher.start() + 1, matcher.end() -1).split("],");
            for (int i = 0; i < str2.length; i++ ){
                list.add(Arrays.asList(str2IntArr(str2[i])));
            }
        }
        int[][] ans = new int[list.size()][list.get(0).size()];
        int i = 0, j = 0;
        for (List<Integer> list1 : list){
            for (Integer key : list1) {
                ans[i][j++] = key;
            }
            i++;
            j = 0;
        }
        return ans;
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
        if (null == arr || arr.length < 1 || parentIndex < 0 || parentIndex > arr.length-1)
            return null;
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
