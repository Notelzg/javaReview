package level_easy;

public class MyLinkedListGeneric<E> {
    transient int size = 0;
    transient Node<E> first;
    transient Node<E> last;

    public boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    public boolean isPostionIndex(int index) {
        return index >= 0 && index <= size;
    }

    /**
     * Initialize your data structure here.
     */
    public MyLinkedListGeneric() {

    }

    private Node<E> node(int index) {
        if (index > (size >> 1)) {
            Node<E> temp = last;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
            return temp;
        } else {
            Node<E> temp = first;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
            return temp;
        }
    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public E get(int index) {
        if (!isElementIndex(index))
            throw new RuntimeException("index is invalid");
        return node(index).item;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(E val) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, val, f);
        if (null == f)
            last = newNode;
        else
            f.prev = newNode;
        first = newNode;
        size++;
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(E val) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, val, null);
        if (null == l)
            first = newNode;
        else
            l.next = newNode;

        last = newNode;
        size++;
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, E val) {
        if (!isPostionIndex(index))
            return;
        /* size 可以为0 */
        if (index == size) {
            addAtTail(val);
            return;
        }
        final Node<E> indexNOde = node(index);
        final Node<E> preNode = indexNOde.prev;
        final Node<E> newNode = new Node<>(preNode, val, indexNOde);
        indexNOde.prev = newNode;
        if (null == preNode)
            first = newNode;
        else
            preNode.next = newNode;
        size++;
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
        if (!isElementIndex(index))
            return;
        final Node<E> succ = node(index);
        final Node<E> prev = succ.prev;
        final Node<E> next = succ.next;
        succ.next = null;
        succ.prev = null;
        succ.item = null;
        if (null == prev) {
            first = next;
        } else {
            prev.next = next;
        }

        if (null == next)
            last = prev;
        else
            next.prev = prev;
        size--;
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    '}';
        }
    }

    public static void main(String[] args) {
        MyLinkedListGeneric linkedList = new MyLinkedListGeneric();
        linkedList.addAtIndex(0, 0);
        linkedList.deleteAtIndex(0);   //链表变为1-> 2-> 3
        linkedList.addAtHead(1);
        linkedList.addAtTail(3);
        linkedList.deleteAtIndex(1);  //现在链表是1-> 3
        linkedList.addAtIndex(1,2);   //链表变为1-> 2-> 3
        System.out.println(linkedList.get(1));
        linkedList.deleteAtIndex(1);  //现在链表是1-> 3
        System.out.println(linkedList.get(1));

    }
}
