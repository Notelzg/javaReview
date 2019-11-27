package option;

public class A {
    static int a  = getA();
    static {
        System.out.println("static code in ready step");
    }

    public static int getA(){
        System.out.println("static method getA");
        return 1;
    }
}
