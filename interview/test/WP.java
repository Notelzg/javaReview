package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 一块钱买一瓶汽水
 * 喝完之后，5个瓶盖可以换一瓶汽水
 * 3个空瓶也可以换一瓶汽水
 * 20块钱最多可以喝多少瓶汽水
 */
public class WP {
    /**
     * 思路1
     * 1块钱一瓶水
     * 5个瓶盖一瓶水
     * 3个空瓶一瓶水
     * 初始水瓶个数是钱的个数
     * 水喝完之后会有 1个瓶盖 1一个空瓶，如果5个瓶盖/3个空瓶 又会生成一瓶水(减去相应的值）
     * 水的个数加1
     * @param money
     * @return
     */
    public int solution(int money){
        if (money <= 0)
            return 0;
       int ans = money;
       int wg = money;
       int h  = 0;
       int g = 0;
       while (wg != 0){
           wg--;
           h++;
           g++;
           if (h == 5){
               wg++;
               h = 0;
               ans++;
           }
           if (g == 3){
               wg++;
               g = 0;
               ans++;
           }
       }
       return ans;
    }

    @Test
    public void test(){
        Assertions.assertEquals(1, solution(1));
        Assertions.assertEquals(2, solution(2));
        Assertions.assertEquals(4, solution(3));
        Assertions.assertEquals(7, solution(4));
        System.out.println(solution(20));
    }
    public static void f(){
        System.out.println("this is test method");
    }
    public static void mian(String[] args){
       WP wp = new WP();
    }
}
