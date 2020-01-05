package level_easy;


import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class AllOne {
    DoubleList head;
    DoubleList tail;

    public void removeIfEmpty(DoubleList dnode) {
        if (size == 0)
            return;
        if (!dnode.isEmpty())
            return;
        //删除空的 doublelist
        DoubleList prev = dnode.prev;
        DoubleList next = dnode.next;
        prev.next = next;
        next.prev = prev;
        dnode.next = null;
        dnode.prev = null;
        dnode = null;
        size--;
    }

    //添加一个doubleList Node,如果节点之后的值不存在
    public void add(Node node, DoubleList dnode) {
        DoubleList doubleList = new DoubleList();
        doubleList.value = node.value;
        DoubleList prev = dnode;
        DoubleList next = dnode.next;
        doubleList.prev = prev;
        doubleList.next = next;
        prev.next = doubleList;
        next.prev = doubleList;

        size++;
    }

    public void addBefore(Node node, DoubleList dnode) {
        if (dnode.value != node.value) {
            add(node, dnode);
            node.doubleList = dnode.next;
            dnode.next.addHead(node);
        }else {
            node.doubleList = dnode;
            dnode.addHead(node);
        }
    }

    public void addAfter(Node node, DoubleList dnode) {
        if (dnode.next.value != node.value) {
            add(node, dnode);
        }
        node.doubleList = dnode.next;
        dnode.next.addHead(node);
    }

    int size;

    class Node {
        String key;
        int value;
        Node before;
        Node after;
        DoubleList doubleList;

        public Node() {
        }

        public Node(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    class DoubleList {
        int size;
        Node first;
        Node last;
        DoubleList next;
        DoubleList prev;
        int value;

        public DoubleList() {
            this.size = 0;
            first = new Node();
            last = new Node();
            first.after = last;
            //指向本身
        }

        public void removeEldToHead(Node node) {
            if (size < 2)
                return;
            //删除元素
            remove(node);
            addTail(node);
        }

        public void addTail(Node node) {
            // 插入队尾
            node.before = this.last.before;
            this.last.before.after = node;
            node.after = last;
            last.before = node;
            this.size++;
        }

        public Node getFirst() {
            return first.after;
        }

        public void addHead(Node node) {
            node.after = this.first.after;
            this.first.after.before = node;
            node.before = this.first;
            this.first.after = node;
            this.size++;
        }

        public Node removeHead() {
            Node ans = first.after;
            remove(ans);
            return ans;
        }

        public void remove(Node node) {
            //为空不能删除
            if (size-- == 0)
                return;
            Node before = node.before;
            Node after = node.after;
            before.after = after;
            after.before = before;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isOnlyOne() {
            return size == 1;
        }
    }

    HashMap<String, Node> map;
    HashMap<Integer, Node> valueMap;
    String maxKey;
    String minKey;
    int maxValue, minValue;

    /**
     * Initialize your data structure here.
     */
    public AllOne() {
        this.map = new HashMap<>();
        this.head = new DoubleList();
        this.tail = new DoubleList();
        head.next = tail;
        tail.prev = head;
        this.valueMap = new HashMap<>();
        this.maxKey = "";
        this.maxKey = "";
        this.maxValue = Integer.MIN_VALUE;
        this.minValue = Integer.MAX_VALUE;
    }

    /**
     * Inserts a new key <Key> with value 1. Or increments an existing key by 1.
     */
    public void inc(String key) {
        Node node = map.get(key);
        if (node == null) {
            node = new Node(key, 1);
            map.put(key, node);
            //节点为1的不存在
            addAfter(node, head);
            return;
        } else {
            node.value++;
            //doublelist只有一个元素的时候，不用删除，直接改变value即可
            DoubleList prev = node.doubleList;
            node.doubleList.remove(node);
            if (node.doubleList.isEmpty()) {
                prev = prev.prev;
                removeIfEmpty(node.doubleList);
            }
            addAfter(node, prev);
        }
    }

    /**
     * Decrements an existing key by 1. If Key's value is 1, remove it from the data structure.
     */
    public void dec(String key) {
        Node node = map.get(key);
        if (node == null) {
            return;
        } else {
            node.value--;
            //doublelist只有一个元素的时候，不用删除，直接改变value即可
            DoubleList prev = node.doubleList.prev;
            node.doubleList.remove(node);
            if (node.doubleList.isEmpty()) {
                removeIfEmpty(node.doubleList);
            }
            if (node.value == 0)
                map.remove(key);
            else
                addBefore(node, prev);
        }
    }

    /**
     * Returns one of the keys with maximal value.
     */
    public String getMaxKey() {
        if (size == 0)
            return "";
        return tail.prev.getFirst().key;
    }

    /**
     * Returns one of the keys with Minimal value.
     */
    public String getMinKey() {
        if (size == 0)
            return "";
        return head.next.getFirst().key;
    }

    @Test
    public void test() {
        // inc a, a ,a b b ,dec a
        String[] str = "a, a ,a, b, b".split(",");
        for (int i = 0; i < str.length; i++) {
            inc(str[i].trim());
            System.out.println("max = " + getMaxKey() + "min = " + getMinKey());
        }
        dec("b");
        System.out.println("max = " + getMaxKey() + "min = " + getMinKey());
        dec("b");
        System.out.println("max = " + getMaxKey() + "min = " + getMinKey());
        //["AllOne","inc",     "inc",     "inc",     "inc", "getMaxKey","inc", "inc","inc","dec","inc","inc","inc","getMaxKey"]
        //  ,   "hello","goodbye","hello","hello",,      "leet","code","leet",
        //  "hello","leet","code","code",
    }
    @Test
    public void test2() {
        // inc a, a ,a b b ,dec a
        String[] str = new String[]{"hello","goodbye","hello","hello"};
        incArr(str);
        str = new String[]{"leet","code","leet"};
        incArr(str);
        dec("hello");
        incArr(new String[]{"leet","code","code"});
   }
   public void incArr(String[] str){
       for (int i = 0; i < str.length; i++) {
           inc(str[i].trim());
           System.out.println("max = " + getMaxKey() + "min = " + getMinKey());
       }
   }
}
