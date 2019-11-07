package deepUnderStandiing;

public class DynamicDispatch {
   static abstract class Human{
        abstract void say();
    }

    static class man extends Human{
        public void say(){
            System.out.println("man");
        }
    }

    static  class woman extends Human{
        public void say(){
            System.out.println("woman");
        }
    }
    public static void main(String[]args){
        Human man = new man();
        Human woman = new woman();
        man.say();
        woman.say();
    }
}
