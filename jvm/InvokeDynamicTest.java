import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

public class InvokeDynamicTest {
    class GrandFather{
        void thingking(){
            System.out.println("i am grandFather");
        }
    }
    class Father  extends GrandFather {
        void thingking(){
            System.out.println("i am Father");
        }
    }
    class Son extends  Father{
        void thingking(){
            try {

                MethodType methodType = MethodType.methodType(void.class);
                GrandFather grandFather = new GrandFather();
                MethodHandle mh    = lookup().findVirtual(grandFather.getClass(),"thingking",methodType).bindTo(grandFather);
                mh.invoke();
                mh    = lookup().findSpecial(GrandFather.class,"thingking",methodType, Son.class);
                mh.invoke(this);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
    public static void main(String[] args){

        (new InvokeDynamicTest().new Son()).thingking();
    }

}
