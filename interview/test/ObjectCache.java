package test;

import java.lang.management.ManagementFactory;

public class ObjectCache{
    static class t extends Thread{
        public void run(){
            while (true) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printMsg();
            }
        }
    }
    public static  void printMsg() {
        Thread t = Thread.currentThread();
        String name = t.getName();
        System.out.println("name=" + name+ " pid=" + t.getId() );
        // get name representing the running Java virtual machine.
        name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name);
        String pid = name.split("@")[0];
        System.out.println("Pid is:" + pid);
    }
    public static void  main(String[] args){
        t tt = new t();
        tt.start();
        printMsg();
    }
}
