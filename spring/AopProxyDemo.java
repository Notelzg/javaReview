import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AopProxyDemo {
    interface subjectIface{
        void fun();
    }

    public static void main(String[] args) {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("invocation Handle");
                return null;
            }
        };

      subjectIface subjectIface = (AopProxyDemo.subjectIface) Proxy.newProxyInstance(AopProxyDemo.class.getClassLoader(), new Class[]{subjectIface.class}, invocationHandler);
      subjectIface.fun();
    }
}
