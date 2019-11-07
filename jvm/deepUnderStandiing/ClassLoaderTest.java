package deepUnderStandiing;

import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderTest {
    int a;
    public  static void main(String args[]) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader classLoader = new ClassLoader(){
            @Override
            public Class<?> loadClass(String name){
                try{
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream inputStream = getClass().getResourceAsStream(fileName);
                    if (inputStream == null){
                        Class rs = super.loadClass(name);
                        System.out.println("supt  " + super.getClass().getName());
                        return rs;
                    }
                    byte[] bytes = new byte[inputStream.available()];
                    inputStream.read(bytes);
                    System.out.println("====================");
                    return  defineClass(name,bytes, 0, bytes.length);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        Class loadClass = classLoader.loadClass("deepUnderStandiing.Test");
        System.out.println(loadClass.getSimpleName());
        Object object = loadClass.newInstance();
        System.out.println(object.getClass());
        System.out.println(object instanceof Test);
        System.out.println(new ClassLoaderTest().a);
    }
}
