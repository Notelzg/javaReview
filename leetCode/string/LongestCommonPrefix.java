package string;



import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 *
 * 如果不存在公共前缀，返回空字符串 ""。
 *
 * 示例 1:
 *
 * 输入: ["flower","flow","flight"]
 * 输出: "fl"
 */
public class LongestCommonPrefix {
    private String findLongestSub(String s1, String s2){
        int i ;
        for (i = 0; i < s1.length() && i < s2.length(); i++){
            if (s1.charAt(i) != s2.charAt(i))
                break;
        }
        return s1.substring(0, i);
    }
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";
        if (strs.length < 2)
            return strs[0];

        String sub = findLongestSub(strs[0], strs[1]);
        for (int i = 2; i < strs.length; i++){
           sub = findLongestSub(sub, strs[i]);
           if (null == sub || sub.length() == 0)
               return "";
        }
        return sub;
    }

    @Test
    public void test(){
       assertEquals("fl", longestCommonPrefix(new String[]{"flower","flow","flight"}));
        assertEquals("flower", longestCommonPrefix(new String[]{"flower"}));
        assertEquals("f", longestCommonPrefix(new String[]{"flower", "f"}));
        assertEquals("", longestCommonPrefix(new String[]{}));
        assertEquals("", longestCommonPrefix(null));
       assertEquals("", longestCommonPrefix(new String[]{"dog","racecar","car"}));
    }
}
