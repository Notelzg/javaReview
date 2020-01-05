package option;

import java.lang.reflect.InvocationTargetException;

public class Test {
    private static int race;
    public static  void increase(){
        race++;
    }
    static  class A{
        static {
            System.out.println("123");
        }
    }
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String s = new String("1123");
        String s1 = new String("123");
        System.out.println(String.join(s));
        System.out.println(s.replace('1', '3'));
        System.out.println(s.replace("1", "3"));
        Long  l = new Long(1);
        System.out.println(l.longValue());
        Long.valueOf("1");
        Long.parseLong("1");
//        Class.forName("option.A");
       Class a = new Test().getClass().getClassLoader().loadClass("option.A");
       A aa = (A)a.newInstance();
       a.getMethod("getA", null).invoke(aa);

    }
}
