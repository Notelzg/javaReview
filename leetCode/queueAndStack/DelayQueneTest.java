package queueAndStack;

import org.junit.jupiter.api.Test;
import sun.awt.windows.ThemeReader;

import java.sql.Time;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * DelayedQueue 使用了一把锁，所以添加的时候，消费的时候都只能进行一个，因为有可能刚添加的就要被消费
 * 所以需要同时锁住整个队列
 */
public class DelayQueneTest {
    static Logger logger = Logger.getLogger(DelayQueneTest.class.getSimpleName());

    static class DelayedDTO implements Delayed{
        //到期时间的lang值
        Long s;
        Long beginTime;
        public DelayedDTO() {
        }

        public DelayedDTO(Long s, Long beginTime) {
            this.s = s;
            this.beginTime = beginTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return  unit.convert(s - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
           return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }
        public void run(){
            logger.info("已经过, "   + (System.currentTimeMillis() - beginTime)/1000 + " 秒!");
        }
    }
    static class Consumer implements Runnable{
        private BlockingQueue blockingDeque;

        public Consumer(BlockingQueue blockingDeque) {
            this.blockingDeque = blockingDeque;
        }

        @Override
        public void run() {
           logger.info("开始消费！");
           try {
               ((DelayedDTO) (blockingDeque.take())).run();
               ((DelayedDTO) (blockingDeque.take())).run();
               ((DelayedDTO) (blockingDeque.take())).run();
           }catch (Exception e){
               e.printStackTrace();
               logger.warning(" eroor" + e.getMessage());
           }
        }
    }
    static class Product implements Runnable{
        final BlockingQueue queue;

        public Product(BlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            logger.info("添加消息到队列，生产者");
            Long begin = System.currentTimeMillis();
            logger.info("beging put");
            queue.offer(new DelayedDTO(begin + 1000L, begin));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("beging put");
            queue.offer(new DelayedDTO(begin + 5000L, begin));
            logger.info("beging put");
            queue.offer(new DelayedDTO(begin + 10000L, begin));
            logger.info("end put");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayedDTO> queue = new DelayQueue<>();
        Thread product = new Thread(new Product(queue));
        Thread custom = new Thread(new Consumer(queue));
        product.join();
        custom.start();
        product.start();
    }
    @Test
    public void test() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("this is thread = " + Thread.currentThread().getName());
            }
        });
        thread.start();
        thread.join();
    }
}
