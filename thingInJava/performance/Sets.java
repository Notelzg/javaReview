package performance;

import java.util.HashSet;
import java.util.Set;

public class Sets {
    public static <T> Set<T> union(Set<T> a, Set<T> b){
        Set<T>    rs =new HashSet<>(a);
        rs.addAll(b);
        return  rs;
    }

    public static <T> Set<T> intersection(Set<T> a, Set<T> b){
        Set<T> rs =new HashSet<>(a);
        rs.retainAll(b);
        return  rs;
    }

    /**
     *
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    public static <T> Set<T> different(Set<T> a, Set<T> b){
        Set<T> rs =new HashSet<>(a);
        rs.removeAll(b);
        return  rs;
    }

    /**
     *
     * @param a
     * @param b
     * @param <T>
     * @return 返回包含除了交集之外的所有元素
     */
    public static <T> Set<T> complement(Set<T> a, Set<T> b){
        Set<T> rs =new HashSet<>(a);
        return different(union(a,b),intersection(a,b));
    }
}
