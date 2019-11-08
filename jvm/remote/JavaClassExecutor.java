package remote;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.ClassLoader.getSystemResourceAsStream;

/**
 * 服务端接收一个字节码数组，然后使用自定义的加载器加载这个类，java的类和加载该类的加载器组合起来是一个类
 * 一个类由多个加载器加载，则表示多各类， 使用 instance 关键字返回结果是 false，所以每次调用execute方法
 * 都会new 一个classloader 以此来实现热加载。
 */
public class JavaClassExecutor {
    /**
     * 接收一个字节码数组，然后通过反射屌用静态函数main方法
     * @param classByte
     * @return
     */
    public static  String execute(byte[] classByte){
       HackSystem.clearBuffer();
       ClassModifier classModifier = new ClassModifier(classByte);
       byte[] modiBytes = classModifier.modifyUTF8Constant("java/lang/System", "remote/HackSystem");
      try {
          OutputStream outputStream = new FileOutputStream("D:\\study\\javaReview\\jvm\\remote\\md.class");
          outputStream.write(modiBytes);
          outputStream.flush();
          outputStream.close();
      }catch (Exception e){
          System.err.println(e.getMessage());
      }
       HotSwapClassLoader loader = new HotSwapClassLoader();
       Class clazz = loader.loadByte(modiBytes);
       try{
           Method method = clazz.getMethod("main", new Class[]{String[].class});
           ///因为main是一个静态变量所以可以使用null
           method.invoke(null, new String[]{null});
       } catch (NoSuchMethodException e) {
           e.printStackTrace();
       } catch (IllegalAccessException e) {
           e.printStackTrace();
       } catch (InvocationTargetException e) {
           e.printStackTrace();
       }
       return HackSystem.getBufferString();
    }
    public static void  main(String[] args) throws IOException {
        InputStream inputStream = new FileInputStream("D:\\study\\javaReview\\jvm\\remote\\Test1.class");
        byte[]  classByte = new byte[inputStream.available()];
        inputStream.read(classByte);
        inputStream.close();
        String rs = execute(classByte);
        System.out.println("rs , " + rs);
    }
}
