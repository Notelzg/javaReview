package level_easy;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class AssignCookies {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int total = 0;
        if (g.length == 0 || s.length == 0 || s[0] < g[0])
            return total;
        int p1 = 0, p2 = 0;
        int i;
        for (i = 0; i < g.length && p1 < s.length; i++){
            if (g[i] > s[p1])
                i--;
            p1++;
        }
        return i;
    }
    @Test
    public void test(){
        int[] g = new int[]{1,2,3};
        int[] s = new int[]{1,1};
       findContentChildren(new int[]{1,2,3}, new int[]{1,1});
        System.out.println(findContentChildren(new int[]{1, 2}, new int[]{1, 2, 3}));
    }
}
