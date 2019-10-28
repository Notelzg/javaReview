import sun.swing.plaf.synth.DefaultSynthStyle;

public class StaticDispatch {
    static abstract class Human{
    }

    static class man extends Human{
    }

    static  class woman extends Human{
    }
    public void say(Human human){
        System.out.println("human");
    }

    public void say(woman human){
        System.out.println("woman");
    }

    public void say(man man){
        System.out.println("man");
    }
    public static void main(String[]args){
        Human man = new man();
        Human woman = new woman();
        StaticDispatch staticDispatch = new StaticDispatch();
        staticDispatch.say (man);
        staticDispatch.say (woman);
    }
}
