

import java.util.function.Consumer;

public class ThreadlocallTest {
    public static Consumer<Object> p = System.out::println;
    public static void main(String[] arg) throws InterruptedException {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        Runnable t1 = ()->{
            threadLocal.set("t1");
            threadLocal.set("t1111");
            StringBuilder sb = new StringBuilder();
            p.accept(sb.append("t1").append("  ").append(threadLocal.get()));
        };
        Runnable t2 = ()->{
            StringBuilder sb = new StringBuilder();
            p.accept(sb.append("t2").append("  ").append(threadLocal.get()));
        };
        Runnable t3 = ()->{
            threadLocal.set("t3");
            StringBuilder sb = new StringBuilder();
            p.accept(sb.append("t2").append("  ").append(threadLocal.get()));
        };
        new Thread(t1).start();
        Thread.sleep(1000*5);
        new Thread(t2).start();
        Thread.sleep(1000*5);
        new Thread(t3).start();
        Thread.sleep(1000*5);
    }
}
