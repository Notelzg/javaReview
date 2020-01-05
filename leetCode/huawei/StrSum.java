package huawei;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.util.Scanner;
    // 本题为考试多行输入输出规范示例，无需提交，不计分。
    //本体思路，把整数使用字符串相加，这样就不存在溢出的情况
    public class StrSum {

        public static String strSum(String a, String b){
            int i = a.length();
            int j = b.length();
            if (i == 0)
                return b;
            if (j == 0)
                return a;
            StringBuilder ans = new StringBuilder(Math.max(i, j) + 1);
            int extra = 0;
            int sum;
            int index = ans.length() - 1;
            i--;
            j--;
            int n1, n2;
            while (i >=0 ||  j >=0){
               n1 =  i>=0 ? a.charAt(i) - '0' : 0;
               n2 =  j>=0 ? b.charAt(j) - '0' : 0;
                sum =  n1 + n2 + extra;
               if (sum > 9){
                   extra = 1;
                   sum -= 10;
               }else {
                   extra = 0;
               }
               ans.append(sum);
                i--;
                j--;
            }
            //一个遍历完，还有一个没有遍历完的情况
            if (extra == 1) {
                ans.append('1');
            }
            return ans.reverse().toString();
        }
        public static void main(String[] args) {
            /**
             * 3
             * 1111111111111111111111111111
             * 23413412323452345
             1111111113356746745656785678             * 2222222222222222222
             */
            Scanner sc = new Scanner(System.in);
            int len = sc.nextInt();
            String ans = "", x;
            for(int i = 0; i < len; i++){
                    x = sc.next();
                    ans = strSum(ans, x);
            }
            System.out.println(ans);
        }
        @Test
        public void test(){
            String[]  str = new String[]{"1111111111111111111111111111", "23413412323452345", "2222222222222222222"};
            String ans = "";
            for (int i = 0; i < str.length ; i++)
               ans =  strSum(ans,str[i]);
            Assertions.assertEquals("1111111113356746745656785678", ans);
        }
    }
