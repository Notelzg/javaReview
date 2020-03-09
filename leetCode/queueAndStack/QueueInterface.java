package queueAndStack;

public interface QueueInterface<T> {
    //入队
    boolean put(T t);
    //出队
    T take();
    //是否为空
    boolean isEmpty();
    //队列大小
    int size();

    class Node<T>{
        Node next;
        T item;
        public Node(T t){
            this.item = t;
        }
    }
}
