package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
/**
 * 10进制转8进制
 */
public class Ten2Eight {
    /**
     *  10进制转8进制
     * 100 / 8 = 12
     * 100 % 8 = 4
     * 12 / 8 = 1
     * 12 % 8 = 4
     * 1 / 8 = 0
     * 1 % 8 = 1
     * 除数作为下一次处理的值
     * 余数作为结果，最后反转
     * @param src
     * @return
     */
    public String Ten2Eight(int src){
      int t = 8;
      if (src <= 0)
          return "0";
      StringBuilder rs = new StringBuilder();
      int div = src / 8;
      int mod = src % 8;
      rs.append(mod);
      while (div != 0){
         mod = div % 8;
         rs.append(mod);
         div = div / 8;
      }
     return rs.reverse().toString();
    }
    /**
     * 最多喝了多少瓶水
     * 思路1
     * 1块钱一瓶水
     * 5个瓶盖一瓶水
     * 3个空瓶一瓶水
     * 初始水瓶个数是钱的个数
     * 水喝完之后会有 1个瓶盖 1一个空瓶，如果5个瓶盖/3个空瓶 又会生成一瓶水(减去相应的值）
     * ,水的个数加1,
     * @param money
     * @return
     */
    public int maxDrinkBottle(int money){
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
        Assertions.assertEquals("144", Ten2Eight(100));
        Assertions.assertEquals("0", Ten2Eight(-100));
    }
}
