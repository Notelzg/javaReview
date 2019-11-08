package deepUnderStandiing;

public class TestClass implements  interface0 {

    private final  int m = 1;
    public final int n = 1;
    public static int a = 0;

    public int inc(){
        return m + 1;
    }
    public TestClass(){
//        m = 1;

    }

    public void f(){
        System.out.println("TestClass" );
        System.out.println(a);
    }

}
interface  interface0{
    int a = 0;
}
interface  interface1{
    int a = 0;
}
class SuperC extends TestClass implements interface1{
    public SuperC(){
//        m = 2;
    }

//    public static int a = 2;
    @Override
    public String toString() {
        return super.toString();
    }

    final int n = 1;
   public static  int m = 2;
   public  void f(){
       System.out.println("TestClass child" );
       super.f();

   }

   public static void main(String[] args){
       TestClass testClass = new TestClass();
       testClass.f();
       testClass = new SuperC();
       testClass.f();

   }

}
