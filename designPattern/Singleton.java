
public class Singleton {
    private Singleton(){

    }
    private String key;
    /* 内部静态类  */
    private static class InnerClss{
       private static Singleton singleton = new Singleton();
    }


    /* 线程变量 */
    private static ThreadLocal<Singleton> threadLocal = new ThreadLocal<Singleton>() {
        @Override
        public Singleton initialValue() {
            return new Singleton();
        }
    }
    ;
    public static  Singleton getInstance(){

        return InnerClss.singleton;
    }
    public static  Singleton getInstance1(){

        return  threadLocal.get();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static void main(String[] args){
        Singleton singleton = Singleton.getInstance1();
        singleton.setKey("thisi s teset");
        System.out.println(singleton.getKey());

    }

}
