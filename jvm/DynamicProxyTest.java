import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {
    interface IHello{
        void say();
    }

    static  class Hellp implements IHello{
        @Override
        public void say(){
            System.out.println("hello world");
        }
    }

    static class DynamicProxy implements InvocationHandler{
        Object orignalObj;
        Object bind(Object orignalObj){
            this.orignalObj = orignalObj;
            return Proxy.newProxyInstance(getClass().getClassLoader(),orignalObj.getClass().getInterfaces(), this);
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("welcome");
           return method.invoke(orignalObj,args);
        }
    }

    public static void main(String[] args){
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        IHello hello = (IHello)new DynamicProxy().bind(new Hellp());
        hello.say();
    }
}
