package level_easy;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 412. Fizz Buzz
 * 写一个程序，输出从 1 到 n 数字的字符串表示。
 * 1. 如果 n 是3的倍数，输出“Fizz”；
 * 2. 如果 n 是5的倍数，输出“Buzz”；
 * 3.如果 n 同时是3和5的倍数，输出 “FizzBuzz”。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/fizz-buzz
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FizzBuZZ {
    public List<String> fizzBuzz(int n) {
        if (n < 0 || n > Integer.MAX_VALUE)
            return new ArrayList<>();
        List<String> list = new ArrayList<>(Math.max(n, 5));
        list.add("1");
        list.add("2");
        list.add("Fizz");
        list.add("4");
        list.add("Buzz");
        if (n <= 5) {
            return list.subList(0, n);
        }
        int i;
        for (i = 5; i < n - 5 ; i += 5) {
            int yushu = i % 3;
            switch (yushu) {
                case 0:
                    list.add(String.valueOf(i + 1));
                    list.add(String.valueOf(i + 2));
                    list.add("Fizz");
                    list.add(String.valueOf(i + 4));
                    list.add("Buzz");
                    break;
                case 1:
                    list.add(String.valueOf(i + 1));
                    list.add("Fizz");
                    list.add(String.valueOf(i + 3));
                    list.add(String.valueOf(i + 4));
                    list.add("FizzBuzz");
                    break;
                case 2:
                    list.add("Fizz");
                    list.add(String.valueOf(i + 2));
                    list.add(String.valueOf(i + 3));
                    list.add("Fizz");
                    list.add("Buzz");
                    break;
            }
        }
        i++;
        while (list.size() < n) {
            if (0 == i % 15)
                list.add("FizzBuzz");
            else if (0 == i % 3)
                list.add("Fizz");
            else if (0 == i % 5)
                list.add("Buzz");
            else
                list.add(String.valueOf(i));
           i++;
        }
        return list;
    }

    public static void main(String[] args) {
        FizzBuZZ fizzBuZZ = new FizzBuZZ();
        fizzBuZZ.fizzBuzz(0).forEach(System.out::println);
        fizzBuZZ.fizzBuzz(6).forEach(System.out::println);
        Logger logger = Logger.getLogger(fizzBuZZ.getClass().getName());
        logger.info("16");
        fizzBuZZ.fizzBuzz(16).forEach(System.out::println);
        logger.info("45");
        fizzBuZZ.fizzBuzz(45).forEach(System.out::println);
    }
}
