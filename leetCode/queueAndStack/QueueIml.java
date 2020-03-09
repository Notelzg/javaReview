package queueAndStack;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class QueueIml<T> implements QueueInterface<T> {
    private AtomicInteger size = new AtomicInteger(0) ;
    private final int capacity;
    private  volatile   Node head;
    private  volatile   Node tail;
    private final ReentrantLock putLock = new ReentrantLock();
    private final ReentrantLock takeLock = new ReentrantLock();

    public QueueIml() {
        head = tail = new DIYNode(null);
        capacity = Integer.MAX_VALUE;
    }
    public QueueIml(Integer capacity) {
        if (null == capacity || capacity < 1)
            throw new IllegalArgumentException();
        head = tail = new DIYNode(null);
        this.capacity = Integer.MAX_VALUE;
    }

    class DIYNode<T> extends Node<T>{
        public DIYNode(T t) {
            super(t);
        }
    }

    @Override
    public boolean put(T t) {
        if (null == t)
            return false;
        if (size.get() >= capacity)
            return false;
        try {
            boolean rs = putLock.tryLock(300, TimeUnit.MILLISECONDS);
            Condition empty = putLock.newCondition();
             putLock.tryLock();
            if (!rs)
                return false;
            tail = tail.next = new DIYNode(t);
            size.incrementAndGet();
            System.out.println("put = " + t);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.println("put InterruptedException");
            return false;
        }catch (Exception e){
            System.err.println("put error ," + e.getMessage());

            return false;
        }finally {
            putLock.unlock();
        }
        return true;
    }

    @Override
    public T take() {
        if (0 == size.get())
            return null;
        try {
            boolean rs = takeLock.tryLock(300, TimeUnit.MILLISECONDS);
            if (!rs)
                return null;
            size.decrementAndGet();
            Node next = head.next;
            head.next = next.next;
            Object resulut =  next.item;
            next.item = null;
            if (head.next == null)
                tail = head;
            System.out.println("take = " + resulut);
            return  (T)(resulut);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.println("take InterruptedException");
        }catch (Exception e){
            System.err.println("take error " + e.getMessage());
        }
        finally {
            takeLock.unlock();
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size.get() == 0;
    }

    @Override
    public int size() {
        return size.get();
    }
    static class  Consume implements Runnable{
        private QueueInterface queue;
        public Consume(QueueInterface queue) {
            this.queue = queue;
        }
        @Override
        public void run() {
            queue.take();
        }
    }
    static class  Product implements Runnable{
        private QueueInterface queue;
        private final  String message;
        public Product(QueueInterface queue, String message) {
            this.queue = queue;
            this.message = message;
        }
        @Override
        public void run() {
             queue.put(message);
        }
    }
    public static  void main(String[] args) throws InterruptedException {
        QueueIml<String> queue = new QueueIml<>();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 10, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
        for (int i = 0; i < 1000; i++){
            if (i %2 == 0){
                poolExecutor.submit(new Product(queue, i + ""));
                continue;
            }
            poolExecutor.submit(new Consume(queue));
        }
    }
}
