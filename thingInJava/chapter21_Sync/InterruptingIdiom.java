package chapter21_Sync;

import java.util.concurrent.TimeUnit;

class NeedsCleanup{
    private final  int id;

    public NeedsCleanup(int id) {
        this.id = id;
        System.out.println("NeedCleanup " + id);
    }

    public void cleanup(){
        System.out.println("Cleaning up " + id );
    }
}
class Block3 implements Runnable{

    private volatile  double d = 0.0d;

    public  void run(){
        try{
           while (!Thread.interrupted()){
               NeedsCleanup cleanup1 = new NeedsCleanup(1);
               try {
                   System.out.println("Sleeping ");
                   TimeUnit.SECONDS.sleep(1);

                   NeedsCleanup cleanup2 = new NeedsCleanup(2);
                   try{
                       System.out.println("calculating ");
                       for (int i = 1; i < 2500000; i++)
                           d = d + (Math.PI + Math.E)/d;
                       System.out.println("Finnished time -consuming operation");
                   }finally {
                      cleanup2.cleanup();
                   }
               }finally {
                   cleanup1.cleanup();
               }
           }
            System.out.println("Exiting via while test");
        }catch (InterruptedException e){
            System.out.println("Exiting InterrupterException ");
        }
    }

}
public class InterruptingIdiom {
    public static void main(String[] args) throws InterruptedException {
        try{
            System.out.println("test");
            return;
        }finally {
            System.out.println("finally");
        }
//        Thread thread  = new Thread(new Block3());
//        thread.start();
//        TimeUnit.MILLISECONDS.sleep(1000000);
//        thread.interrupt();

    }
}
