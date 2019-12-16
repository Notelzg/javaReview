package string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
 *
 * 示例 1:
 *
 * 输入: num1 = "2", num2 = "3"
 * 输出: "6"
 * 示例 2:
 *
 * 输入: num1 = "123", num2 = "456"
 * 输出: "56088"
 * 说明：
 *
 * num1 和 num2 的长度小于110。
 * num1 和 num2 只包含数字 0-9。
 * num1 和 num2 均不以零开头，除非是数字 0 本身。
 * 不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。
 */
public class Multiply {
    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0"))
            return "0";
        int[] n1 = new int[num1.length()];
        int[] n2 = new int[num2.length()];
        /* 初始化, 转为数字 */
        for (int i = 0; i < n1.length; i++)
            n1[i] = num1.charAt(i)-'0';
        for (int i = 0; i < n2.length; i++)
            n2[i] = num2.charAt(i)-'0';

        // 两术想乘, 最大长度是
        int maxLen = n1.length + n2.length;
        int[] ans = new int[maxLen];
        //计算结果
        int m = 0;
        //计算结果存储的下标, 根据乘法的特性, 123*45 ,拆分为 45*3, 45*2, 45*1,
        // 结果数组长度为 3+2 =5, 下标从0--4, 所以个位下标是 i+j+1, 使用倒序便利
        // 45*3, 拆为, 4*3 5*3, 4是个位, 4是十位,但是由于使用大端法存储,值越大存储越靠前
        //所以十位下标是0, 个位下标为1,
        int index = 0;
        for (int i = n1.length - 1; i >= 0; i--) {
            for (int j = n2.length - 1; j >= 0; j--) {
                m = n1[i]  * n2[j];
                index = i + j + 1;
                // 加上已经存在的值
                m  = ans[index] + m;
                // 大于10 进1
                while (m > 9){
                    ans[index] = m % 10;
                    m = ans[--index] + m/10;
                }
                ans[index] = m;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        index = 0;
        while (ans[index] == 0)
            index++;
        while (index < maxLen)
            stringBuilder.append(ans[index++]);
        return stringBuilder.toString();
    }

    @Test
    public void test(){
        Assertions.assertEquals("56088",multiply("123", "456"));
    }
}
