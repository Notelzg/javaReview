package zijietiaodong;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Objects;

public  class MyLinkedList {
    private class Node {
        private int val;
        private Node next;
        private Node prev;


        public Node(int val) {
            this.val = val;
            this.next = null;
            this.prev = null;
        }

    }

    Node root = null;
    Node last = null;
    int size;
    /**
     * Initialize your data structure here.
     */
    public MyLinkedList() {
        root = new Node(-1);
        root.next = root;
        root.prev = root;
        last = root;
        size = 0;
    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
        if (index >= size || index < 0)
            return  -1;
        Node temp = getNode(index);
        return temp.val;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
        addAtIndex(0, val);
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
        addAtIndex(size, val);
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
        if (index < 0) {
            addAtHead(val);
            return;
        }
        Node head = root;
        for (int i = 0; i < index; i++){
            head = head.next;
        }
        Node node = new Node(val);
        node.next = head.next;
        node.prev = head;
        head.next = node;
        if (index == size) {
            last = node;
            root.prev = last;
        }
        size++;
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size)
            return;
        Node heal = root;
        for (int i = 0; i < index; i++){
            heal = heal.next;
        }
        Node temp = heal.next;
        heal.next = temp.next;
        temp.next = null;
       size--;
    }

    private Node getNode(int index) {
        Node temp = root.next;
        for (int i = 0 ; i < index; i++){
            temp = temp.next;
        }
        return temp;
    }

    public void printNode(){
        if (null == root)
            System.out.println("is empty");
        System.out.print("\n" + root.val);
        Node temp = root;
        while (temp.next != null){
            temp = temp.next;
            System.out.print("-->" + temp.val);
            System.out.flush();
        }
        System.out.println();
    }

    @Test
    public void test() {
        /**
         * ["MyLinkedList","addAtHead","addAtHead","addAtHead", * "addAtIndex"
         * ,"deleteAtIndex","addAtHead","addAtTail","get","addAtHead","addAtIndex","addAtHead"]
         * [[],[7],[2],[1],[3,0],
         * [2],[6],[4],
         * [4],[4],[5,0],[6]]
         *
          ["MyLinkedList","addAtHead","addAtTail","deleteAtIndex","addAtTail","addAtIndex","addAtIndex","deleteAtIndex","deleteAtIndex","addAtTail","addAtIndex","addAtTail"]
          [[],[7],[0],[1],[5],[1,1],[2,6],[2],[1],[7],[1,7],[6]]
         ["MyLinkedList","addAtHead","addAtTail","deleteAtIndex","addAtTail","addAtIndex","addAtIndex","deleteAtIndex","deleteAtIndex","addAtTail","addAtIndex","addAtTail"]
         [[],[7],[0],[1],[5],[1,1],[2,6],[2],[1],[7],[1,7],[6]]


         {"addAtHead","get","addAtIndex","addAtIndex","deleteAtIndex","addAtHead","addAtHead","deleteAtIndex","addAtIndex","addAtHead","deleteAtIndex"}
         {{},{9},{1},{1,1},{1,7},{1},{7},{4},{1},{1,4},{2},{5}}
         [null,null,null,null,null,null,null,null,null,null,null,61,null,null,61,null,null,null,null,null,null,85,null,null,37,null,null,null,null,null,null,null,null,23,null,null,null,null,null,null,null,null,null,null,-1,55,null,null,null,null,null,31,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,8,null,null,null,null,null,null,null,null,null,6,47,null,55,null,null,null,null,null,null,null,85,null,null,null,null,89,null,85,null,null,59,null,null]
         [null,null,null,null,null,null,null,null,null,null,null,61,null,null,61,null,null,null,null,null,null,85,null,null,37,null,null,null,null,null,null,null,null,23,null,null,null,null,null,null,null,null,null,null,-1,95,null,null,null,null,null,31,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,8,null,null,null,null,null,null,null,null,null,6,47,null,23,null,null,null,null,null,null,null,93,null,null,null,null,48,null,93,null,null,59,null,null]
         {"MyLinkedList","addAtHead","addAtTail","addAtTail","addAtTail","addAtTail","addAtTail","addAtTail","deleteAtIndex","addAtHead","addAtHead","get","addAtTail","addAtHead","get","addAtTail","addAtIndex","addAtTail","addAtHead","addAtHead","addAtHead","get","addAtIndex","addAtHead","get","addAtHead","deleteAtIndex","addAtHead","addAtTail","addAtTail","addAtIndex","addAtTail","addAtHead","get","addAtTail","deleteAtIndex","addAtIndex","deleteAtIndex","addAtHead","addAtTail","addAtHead","addAtHead","addAtTail","addAtTail","get","get","addAtHead","addAtTail","addAtTail","addAtTail","addAtIndex","get","addAtHead","addAtIndex","addAtHead","addAtTail","addAtTail","addAtIndex","deleteAtIndex","addAtIndex","addAtHead","addAtHead","deleteAtIndex","addAtTail","deleteAtIndex","addAtIndex","addAtTail","addAtHead","get","addAtIndex","addAtTail","addAtHead","addAtHead","addAtHead","addAtHead","addAtHead","addAtHead","deleteAtIndex","get","get","addAtHead","get","addAtTail","addAtTail","addAtIndex","addA
         {{38},{66},{61},{76},{26},{37},{8},{5},{4},{45},{4},{85},{37},{5},{93},{10,23},{21},{52},{15},{47},{12},{6,24},{64},{4},{31},{6},{40},{17},{15},{19,2},{11},{86},{17},{55},{15},{14,95},{22},{66},{95},{8},{47},{23},{39},{30},{27},{0},{99},{45},{4},{9,11},{6},{81},{18,32},{20},{13},{42},{37,91},{36},{10,37},{96},{57},{20},{89},{18},{41,5},{23},{75},{7},{25,51},{48},{46},{29},{85},{82},{6},{38},{14},{1},{12},{42},{42},{83},{13},{14,20},{17,34},{36},{58},{2},{38},{33,59},{37},{15},{64},{56},{0},{40},{92},{63},{35},{62},{32}}


         */
        try {
            Class clzz =  Class.forName("zijietiaodong.MyLinkedList");
            String[] methodArr = {"addAtHead","get","addAtIndex","addAtIndex","deleteAtIndex","addAtHead","addAtHead","deleteAtIndex","addAtIndex","addAtHead","deleteAtIndex"};
            Object link = clzz.newInstance();
            int[][] param = {{9},{1},{1,1},{1,7},{1},{7},{4},{1},{1,4},{2},{5}};
            for (int i = 0; i < param.length; i++) {
                if (param[i].length == 1) {
                    Method method = clzz.getMethod(methodArr[i], int.class);
                    method.invoke(link, param[i][0]);
                }else if (param[i].length == 2) {
                    Method method = clzz.getMethod(methodArr[i], int.class, int.class);
                    method.invoke(link, param[i][0], param[i][1]);
                }
                System.out.println(methodArr[i] + "=====param index: " +i);
                Method method = clzz.getMethod("printNode");
                method.invoke(link);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
