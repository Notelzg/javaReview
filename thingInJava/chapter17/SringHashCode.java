package chapter17;

public class SringHashCode {

    public static void main(String[] args){

        String[] hellos = "hello hellos".split(" ");
        String a = new String("12");
        String b = new String("12");
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(hellos.hashCode());
        System.out.println(hellos[0].hashCode());
        System.out.println(hellos[1].hashCode());
    }



}
