package chapter21_Sync;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public  class Fibonacci{
    private int n;
    public Fibonacci(){}
    public Fibonacci(int n ){
        this.n = n;
    }
    public static int f(int n) {
        if (n < 0)
            return 0;
        if (0 == n || 1 == n)
            return 1;
        if (2 == n)
            return  2;
        return f(n - 3) + f(n-2) + f(n - 1);
    }

    public static  String f(int count, int n){
        Random random = new Random(count);
        StringBuilder sb = new StringBuilder("count= " + count + "  ");
        for (int i = 0; i < count; i++)
           sb.append(f(n--)).append(" ");
        System.out.println(sb);
        return sb.toString();
    }
    public static  Integer[] fInt(int count, int n){
        Integer[] sb = new Integer[count];
        for (int i = 0; i < count; i++)
            sb[i]  = f(n--);
//        List<Integer> list = Arrays.asList(sb);
        int[] sb1 = new int[count];
        String[] sb2 = new String[count];
        List<Object>  l   = Arrays.asList(sb2);
        l = Arrays.asList(sb);

       return  null;
    }

}

