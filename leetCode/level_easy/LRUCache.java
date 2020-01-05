package level_easy;

import chapter20_Annotation.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.commons.util.LruCache;

import javax.xml.soap.Node;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 思路1， 最近，最少访问算法
 * 需要一个计数器记录所有元素的访问次数，当一个元素put/get时候访问次数增加
 * 其他元素的访问次数应该减少。
 * put的时候 如果当前存储的元素个数等于capacity的时候，把访问
 * 次数最少的那个元素清除，然后把新元素存储，并增加访问次数，减少其他元素访问次数。
 * 这种思路肯定可以实现，但是每次插入一个元素需要更新所有元素的访问计数，而且需要
 * 找到最小的那个时间复杂度是 O(n^2), 空间复杂度 O(n)
 * 使用一个list存储
 */
public  class LRUCache{
    private int size;
    private HashMap<Integer, Node> map;
    private DoubleList list;
    class Node{
        int key;
        int val;
        Node before;
        Node after;

        public Node(int key, int val, Node before, Node after) {
            this.key = key;
            this.val = val;
            this.before = before;
            this.after = after;
        }
    }
    class DoubleList{
        int size;
        Node first;
        Node last;

        public DoubleList() {
            this.size = 0;
            first = new Node(0, 0, null, null);
            last = new Node(0, 0, first, null);
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
       public void addTail(Node node){
           // 插入队尾
           node.before = this.last.before;
           this.last.before.after = node;
           node.after = last;
           last.before = node;
           this.size++;
       }
       public void addHead(Node node){
           node.after = this.first.after;
           this.first.after.before = node;
           node.before = this.first;
           this.first.after = node;
           this.size++;
       }
        public Node removeHead(){
            Node ans = first.after;
            remove(ans);
            return ans;
        }
       public void remove(Node node){
            //为空不能删除
            if (size == 0)
                return;
           Node before = node.before;
           Node after = node.after;
           before.after = after;
           after.before = before;
           this.size--;
       }
    }
    public LRUCache(int capacity) {
        this.size = capacity;
        this.map = new HashMap<>(capacity);
        this.list = new DoubleList();
    }

    public int get(int key) {
        Node ans = map.get(key);
        if (null == ans)
            return -1;
        //把key从链表删除，并且放到队头
        list.removeEldToHead(ans);
        return  ans.val;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)){
            //更新值
            Node temp = map.get(key);
            temp.val = value;
           list.removeEldToHead(temp);
        }else {
            //cache满了，删除队尾元素
            if (list.size == this.size){
                //元素出队, 删除map中key
               map.remove(list.removeHead().key);
            }
            //插入元素到map和list中
            Node newNode = new Node(key,value, null, null);
            map.put(key, newNode);
            list.addTail(newNode);
        }
    }

    @Test
    public void test(){
        LRUCache cache = new LRUCache( 2 /* 缓存容量 */ );
        cache.put(1, 1);
        cache.put(2, 2);
        Assertions.assertEquals(1,cache.get(1));       // 返回  1
        cache.put(3, 3);    // 该操作会使得密钥 2 作废
        Assertions.assertEquals(-1,cache.get(2));       // 返回 -1 (未找到)
        cache.put(4, 4);    // 该操作会使得密钥 1 作废
        Assertions.assertEquals(-1, cache.get(1));       // 返回 -1 (未找到)
        Assertions.assertEquals(3, cache.get(3));       // 返回  3
        Assertions.assertEquals(4, cache.get(4));       // 返回  4
    }
    static class LRU extends LinkedHashMap<Integer, Integer>{
        private int capacity;

        public LRU(int capacity) {
            super(capacity, 0.75F, true);
            this.capacity = capacity;
        }

        public int get(int key) {
            return super.getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            super.put(key, value);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }
    }

    public static void main(String[] args){
        /**
         * ["LRUCache","put","put","get","put","put","get"]
         * [[2],[2,1],[2,2],[2],[1,1],[4,1],[2]]
         */
         LRU cache = new LRU( 2 /* 缓存容量 */ );



        cache.put(1, 1);
        cache.put(2, 2);
        Assertions.assertEquals(1,cache.get(1));       // 返回  1
        cache.put(3, 3);    // 该操作会使得密钥 2 作废
        Assertions.assertEquals(-1,cache.get(2));       // 返回 -1 (未找到)
        cache.put(4, 4);    // 该操作会使得密钥 1 作废
        Assertions.assertEquals(-1, cache.get(1));       // 返回 -1 (未找到)
        Assertions.assertEquals(3, cache.get(3));       // 返回  3
        Assertions.assertEquals(4, cache.get(4));

    }
}


/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

