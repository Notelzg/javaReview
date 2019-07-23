package chapter21_Sync;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class SleepBlocked implements Runnable{
   public void  run(){
       try{
           TimeUnit.SECONDS.sleep(1);
       }catch (InterruptedException e){
           System.out.println("interruptingException");
       }
       System.out.println("Exiting SleepBlocked.run()");
    }
}
class IOBlocked implements  Runnable{
    private  InputStream in ;

    public IOBlocked(InputStream in) {
        this.in = in;
    }

    public  void run(){
       try {
           System.out.println("waiting for read");
           in.read();
       } catch (IOException e) {
           if (Thread.currentThread().isInterrupted())
               System.out.println("Interrupted from blocked I/O");
           else
               throw new RuntimeException(e);
       }finally {
           System.out.println("finally clean");
       }
        System.out.println("Exiting IOBlocked.run()");
    }
}
class SynchronizedBlocked implements  Runnable{
    public synchronized  void f(){
        while (true){
            Thread.yield();
        }
    }

    public SynchronizedBlocked() {
        new Thread(){
            public void run(){
                f();
            }
        }.start();
    }

    public void run(){
        System.out.println("Teying to call f()");
        f();
        System.out.println("Exiting Synchronized.run()");
    }
}
public class Interrupt {
    private static ExecutorService exec = Executors.newCachedThreadPool();
    static void test(Runnable runnable) throws  InterruptedException{
        Future<?> future = exec.submit(runnable);
        TimeUnit.MICROSECONDS.sleep(100);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Interrupting " + runnable.getClass().getName());
        future.cancel(true);
        System.out.println("Interrupting sent to " + runnable.getClass().getName());
    }
    public static  void main(String[] args) throws InterruptedException, IOException {
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        System.in.close();
        test(new SynchronizedBlocked());
        TimeUnit.SECONDS.sleep(3);
        System.exit(0);

    }
}
