package chapter21_Sync;

import chapter20_Annotation.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Practice {
    public static void main(String[] args) {
//       practice2();
//        practice3();

        Fibonacci.fInt(5, 5);
    }
    public static void practice4() {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Callable<List<Integer>>(){
                @Override
                public List<Integer> call() throws Exception {
                    return (Arrays.asList(Fibonacci.fInt(finalI, finalI)));
                }
            };
        }
    }

    public static void practice3() {
        ExecutorService[] exec = {
                Executors.newCachedThreadPool(),
                Executors.newFixedThreadPool(5),
                Executors.newSingleThreadExecutor()
        };
        for (int j = 0; j < exec.length; j++) {
            System.out.println("Executor is " + exec[j].getClass().getName() + "  Starting ");
            for (int i = 0; i < 10; i++) {
                int finalI = i;
                exec[j].execute(new Runnable() {
                    @Override
                    public void run() {
                        Fibonacci.f(finalI, finalI);
                    }
                });
            }
            exec[j].shutdown();
            System.out.println("Executor is " + exec[j].getClass().getName() + "   Shutting ");
        }
    }

    public static void practice2() {
        ArrayList arrayList = new ArrayList();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Fibonacci.f(finalI, finalI);
                }
            }).start();
        }
    }
}
