package string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
 *
 * 换句话说，第一个字符串的排列之一是第二个字符串的子串。
 */
public class CheckInClusion {
    public boolean checkInclusion(String s1, String s2) {
        if (null == s1 || s2 == null || s1.isEmpty() || s2.isEmpty())
            return false;

        if (s1.length() > s2.length())
            return false;
        // 筛掉按照顺序排序的s1
        if (s2.indexOf(s1) != -1)
            return true;

        int[] s1Index = new int[s2.length()];
        Map<Character, Integer> map1 = new HashMap<>(s1.length());
        for (int i = 0; i < s1.length(); i++){
           map1.merge(s1.charAt(i), 1, (k,v)->v+1) ;
        }
        int start = 0;
        for (int i = 0; i < s2.length(); i++){
           if (map1.containsKey(s2.charAt(i)))
               s1Index[start++] = i;
        }
        // 如果还存在" 则表示元素不存在
        if (start < s1.length())
            return false;


        Arrays.sort(s1Index);
        if (s1Index[s2.length() -1] - s1Index[s2.length() - s1.length()] == s1.length() -1)
            return true;
        return false;
    }
    public boolean checkInclusion2(String s1, String s2) {
        if (s1.length() > s2.length())
            return false;
        // 筛掉按照顺序排序的s1
        if (s2.indexOf(s1) != -1)
            return true;
        int[] s1map = new int[26];
        int[] s2map = new int[26];
        for (int i = 0; i < s1.length(); i++){
            s1map[s1.charAt(i)-'a']++;
            s2map[s2.charAt(i)-'a']++;
        }
        for (int i = 0; i < s2.length() - s1.length(); i++){
            if (match(s1map, s2map))
                return true;
            s2map[s2.charAt(i+s1.length()) -'a']++;
            s2map[s2.charAt(i) -'a']--;
        }
        return match(s1map, s2map);
    }
     public boolean match(int[] s1, int[] s2) {
        if (s1.length != s2.length)
            return false;
        for (int i = 0; i < s1.length; i++){
            if (s1[i] != s2[i])
                return false;
        }
        return true;

     }


    @Test
    public void test(){
        Assertions.assertEquals(false, checkInclusion2("hello" , "ooolleoooleh"));
        Assertions.assertEquals(true, checkInclusion2("ab" , "eidbaooo"));
        Assertions.assertEquals(true, checkInclusion2("a" , "dcda"));
        Assertions.assertEquals(true, checkInclusion2("adc" , "dcda"));
        Assertions.assertEquals(false,checkInclusion2("ab" , "eidboaoo"));
    }
}
