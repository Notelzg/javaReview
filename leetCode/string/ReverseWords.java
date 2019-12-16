package string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.sql.rowset.Joinable;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 给定一个字符串，逐个翻转字符串中的每个单词
 * 说明：
 *
 * 无空格字符构成一个单词。
 * 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
 * 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
 *
 */
public class ReverseWords {
    public String reverseWords(String s) {
        if (null == s || s.isEmpty())
            return s;
        s = s.trim();
        String[] ans =  s.split("\\s+");
        StringBuilder stringBuilder = new StringBuilder(ans.length);
        for (int i = ans.length - 1; i > 0; i--)
           stringBuilder.append(ans[i] + " ");
        stringBuilder.append(ans[0]);
        return stringBuilder.toString();
    }

   @Test
    public void test(){
       Assertions.assertEquals("abc", reverseWords("  abc  "));
       Assertions.assertEquals("c c ab", reverseWords("  ab  c  c "));
       Assertions.assertEquals("blue is sky the", reverseWords("the sky is blue"));
       Assertions.assertEquals("example good a", reverseWords("a good   example"));
   }
}
