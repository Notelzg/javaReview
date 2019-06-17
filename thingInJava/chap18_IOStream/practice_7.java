package chap18_IOStream;

import java.io.*;
import java.util.*;

public class practice_7 {
    public static void main(String[] args){

        try {
            OSExecute.command("javap thingInJava/reflect/ShowMethod".replaceAll("/", "."));
            PPrint.pprint(Directory.walk(".", ".*ShowMethod\\.class$").files);
            System.nanoTime();
           // practice7("/Users/lizhigang/Documents/javaReview/thingInJava/chap18_IOStream/conclude_18.md");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static  void practice7(String  path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path ));
        List<String> rs = new ArrayList<String>();
        String s;
        while(null != (s = br.readLine())){
           rs.add(s);
        }
        ListIterator fwd = rs.listIterator();
        ListIterator rev = rs.listIterator(rs.size());
        Object tmp = fwd.next();
        fwd.set(rev.previous());
        rev.set(tmp);
        Collections.reverse(rs);
        PPrint.pprint(rs);
    }
}
