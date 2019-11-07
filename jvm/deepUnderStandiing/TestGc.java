package deepUnderStandiing;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestGc {
    private static final  int _1MB = 1024 * 1014;
    public static void testAllocation(){
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];

    }
    public int add(int a, int b){
        return  a+b;
    }

    public static void main(String args[]) throws IOException, InterruptedException {
        TestGc testGc =  new TestGc();
        TimeUnit.SECONDS.sleep(10);
        for (int i=0; i < 100000000; i++) {
            int a = (int) Math.round(Math.random() * 1000);
            int b = (int) Math.round(Math.random() * 1000);
            System.out.println(testGc.add(a, b));
        }
//        testAllocation();
//        for (int i = 0; i < 200; i++){
//            new Thread(new SynAddRunable(1,2)).start();
//            new Thread(new SynAddRunable(2,1)).start();
//        }
    }

    static class SynAddRunable implements  Runnable{
        int a, b;
        public  SynAddRunable(int a, int b){
            this.a  = a;
            this.b  = b;
        }
        @Override
        public  void run(){
           synchronized (Integer.valueOf(a)){
               synchronized (Integer.valueOf(b)){
                   System.out.println(a + b);
               }
           }
        }

    }

}
