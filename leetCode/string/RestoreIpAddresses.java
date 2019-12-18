package string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.util.ArrayList;
import java.util.List;

public class RestoreIpAddresses {
    private boolean j(String str) {
        if (str.length() == 1)
            return true;
        if (str.length() == 2)
            if (str.charAt(0) > '0')
                return true;
            else
                return false;
        //长度为3的情况
        if (str.charAt(0) == '0' || str.charAt(0) > '2')
            return false;
        if (str.charAt(0) == '1')
            return true;
        if (str.charAt(1) > '5')
            return false;
        else  if (str.charAt(1) == '5' && str.charAt(2) > '5')
            return false;
        return true;
    }
    public List<String> restoreIpAddresses(String s) {
        List<String> ans = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        if (s.length() < 4)
            return ans;
        if (s.length() == 4 || s.length() == 12) ;
        String  s1;
        String  s2;
        String  s3;
        String  s4;
        for (int i = 1; i < 4; i++ )
            for (int j = 1; j < 4; j++ )
                for (int k = 1; k < 4; k++ )
                    for (int l = 1; l < 4; l++ ) {
                        if ((i + j + k + l) != s.length())
                            continue;
                        s1 = s.substring(0, i);
                        s2 = s.substring(i, i+j);
                        s3 = s.substring(i + j, i+j+k);
                        s4 = s.substring(i+k+j, l+k+j+i);
                        if (j(s1) && j(s2) && j(s3) && j(s4)) {
                            stringBuilder.append(s1 + ".").append(s2 + ".").append(s3 + ".").append(s4);
                            ans.add(stringBuilder.toString());
                            stringBuilder.delete(0, stringBuilder.length());
                        }
                    }
        return ans;
    }

    public List<String> restoreIpAddresses1(String s) {
        List<String> ans = new ArrayList<>();
        if (s.length() < 4)
            return ans;
        StringBuilder stringBuilder = new StringBuilder();
        int endIndex = 0;
        for (int i = 3; i > 0; i--)
            for (int j = 3; j > 0; j--)
                for (int k = 3; k > 0; k--)
                    for (int l = 3; l > 0; l--) {
                        if ((i + j + k + l) != s.length())
                            continue;
                        int s1 = Integer.parseInt(s.substring(0, l));
                        int s2 = Integer.parseInt(s.substring(l, l + k));
                        int s3 = Integer.parseInt(s.substring(l + k, l + k + j));
                        int s4 = Integer.parseInt(s.substring(l + k + j, l + k + j + i));
                        if (s1 <= 255 && s2 <= 255 && s3 <= 255 && s4 <= 255)
                            stringBuilder.append(s1 + ".").append(s2 + ".").append(s3 + ".").append(s4);
                        if (stringBuilder.length() == s.length() + 3)
                            ans.add(stringBuilder.toString());
                        stringBuilder.delete(0, stringBuilder.length());
                    }
        return ans;
    }

    @Test
    public void test() {
        restoreIpAddresses("010010").stream().forEach(System.out::println);
        System.out.println();
        restoreIpAddresses1("010010").stream().forEach(System.out::println);
        System.out.println();
        restoreIpAddresses("010010").stream().forEach(System.out::println);
        System.out.println();
        restoreIpAddresses("25525511135").stream().forEach(System.out::println);
    }
}
