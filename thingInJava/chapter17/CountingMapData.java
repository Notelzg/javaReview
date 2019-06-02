package chapter17;

import java.util.*;

public class CountingMapData
        extends AbstractMap<Integer, String> {

    Set<Map.Entry<Integer, String>> entries =
            new LinkedHashSet<>();
    private int size;
    private static String[] chars =
            "A B C D E F H I J K L M N O P Q R S T U V W X Y Z".split(" ");

    public CountingMapData(int size) {
        if (size < 0) this.size = 0;
        this.size = size;
    }

    private static class Entry
            implements Map.Entry<Integer, String> {
        int index;

        public Entry(int index) {
            this.index = index;
        }

        @Override
        public boolean equals(Object obj) {
            return Integer.valueOf(index).equals(obj);
        }

        @Override
        public Integer getKey() {
            return index;
        }

        @Override
        public String getValue() {
            return
                chars[index % chars.length] +
                Integer.toString(index / chars.length);
        }

        @Override
        public String setValue(String value) {
            throw new UnsupportedOperationException();
        }

        public int hashCode() {
            return Integer.valueOf(index).hashCode();
        }
    }

    @Override
    public Set<Map.Entry<Integer, String>> entrySet() {
        for (int i = 0; i < size; i++) {
            entries.add(new Entry(i));

        }
        return entries;
    }

    public String put(Integer key, String value) {
        Set<Map.Entry<Integer, String>> set = entrySet();
                set.add(new Entry(key));
                set.size();
        return "";
    }
    public void testCollection(){
        Collection<String> c = new ArrayList<>(Arrays.asList("A b c d e f g h".split(" ")));
        List<?> c2 = Arrays.asList("A b c d e f g h".split(" "));
        Object[][] o = new Object[10][2];
        o[0] = new Object[]{"key", "value"};
        Object[] t = {"1", "2"} ;
        o[1] = t;
        Arrays.deepToString(o);
        System.out.println(Arrays.toString(t));
        System.out.println(Arrays.deepToString(o));
        c.clear();
        c2.clear();
        String[] s = c.toArray(new String[100]);
        System.out.println(Arrays.toString(s));
    }
    public  static void main(String[] args){
        CountingMapData t = new CountingMapData(1);
        t.testCollection();
        t.toString();
        t.put(100,"");
        System.out.println(t);
    }
}
