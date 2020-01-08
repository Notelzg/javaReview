package test;
public class ObjectCache {
    public static void  main(String[] args){
        String str = new String("abc");
        String str2 = new String("abc");
        System.out.println(str2 == str);
        String str3 = "abc";
        String str4 = "abc";
        System.out.println(str3 == str4);


    }
}
