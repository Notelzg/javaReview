package chap18_IOStream;

import java.util.Arrays;
import java.util.Collection;

public class PPrint {
    public static String pformat(Collection<?> collection){
        if (collection.size() == 0)
            return "[]";
        StringBuilder  rs = new StringBuilder("[");
        for (Object o : collection){
            if (collection.size() != 1)
                rs.append("\n  ");
            rs.append(o);
        }
        if (collection.size() != 1)
            rs.append("\n  ");
       rs.append("]") ;
       return  rs.toString();
    }
    public static  void pprint(Collection<?> collection){
        System.out.println(pformat(collection));
    }
    public static  void pprint(Object[] array){
        System.out.println(pformat(Arrays.asList(array)));
    }
}
