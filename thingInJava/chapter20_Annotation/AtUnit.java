// {RunByHand}
package chapter20_Annotation;

import chap18_IOStream.BinaryFile;
import chap18_IOStream.ProcessFiles;

import java.beans.MethodDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;


public class AtUnit implements ProcessFiles.Strategy {
    static  Class<?> testClass ;
    static List<String> failedTests = new ArrayList<>();
    static  long testRun = 0;
    static  long failures = 0;
    public static  void main(String[] args){
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
        new ProcessFiles(new AtUnit(), "class").start(args);
        if (failures == 0)
            System.out.println("Success: " + testRun + " was tested");
        else {
            System.out.println("Success: " + testRun + " was tested");
            System.out.println("Failure test: " + failures);
            for (String test : failedTests)
                System.out.println(" " + test);
        }
    }

    @Override
    public void process(File file) throws IOException {
        try {
            String className = ClassNameFinder.thisClass(BinaryFile.read(file));
            if (!className.contains("."))
                return; //Ignore unpackage calss
            testClass = Class.forName(className);
            TestMethos testMethos = new TestMethos();
            Method creator = null;
            Method clean = null;

            for (Method method : testClass.getDeclaredMethods()){
                testMethos.addIfTestMethod(method);
                if (creator == null)
                    creator = checkForCreateMethod(method);
                if (clean == null)
                    clean = checkForCleanUpMethod(method);
            }

            if (testMethos.size() > 0){
                if (creator == null)
                    try{
                        if(!Modifier.isPublic(testClass.getDeclaredConstructor().getModifiers()))
                            System.out.println("Error " + testClass + "default constructor must be public ");
                        System.exit(1);
                    }catch (NoSuchMethodException e){

                    }
                System.out.println(testClass.getName());
        }
        for (Method method : testMethos){
            System.out.println(" ." + method.getName() + "  ");
            try{
                Object testObject = createTestObject(creator);
                boolean success = false;
                try{
                    if (method.getReturnType().equals(boolean.class))
                        success = (boolean)method.invoke(testMethos);
                    else {
                        method.invoke(testObject);
                        success  = true;
                    }
                }catch (InvocationTargetException e){
                    System.out.println(e.getCause());
                }
                System.out.println(success ? " " : "(failed)");
                testRun++;
                if (!success){
                    failures++;
                    failedTests.add(testClass.getName() + " : " + method.getName());
                }
                if (clean != null)
                    clean.invoke(testObject, testObject);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    static class TestMethos extends ArrayList<Method>{
       void addIfTestMethod(Method method) {
           if (method.getAnnotation(Test.class) == null)
               return;
           if (!(method.getReturnType().equals(boolean.class) || method.getReturnType().equals(void.class)))
               throw new RuntimeException("@Test method must return void or boolean ");
           method.setAccessible(true);
           add(method);
       }
    }

    private static Method checkForCreateMethod(Method method){
        if (method.getAnnotation(TestObjectCreate.class) == null)
            return  null;
        if (!method.getReturnType().equals(testClass))
            throw new RuntimeException("@TestObjectCreate must return instance of class to be tested");
        if ((method.getModifiers() & Modifier.STATIC)  < 1)
            throw new RuntimeException("@TestObjectCreate must be static");
        method.setAccessible(true);
        return method;
    }

    private static Method checkForCleanUpMethod(Method method){
        if (method.getAnnotation(TestObjectClean.class) == null)
            return null;
        if (!method.getReturnType().equals(void.class))
            throw new RuntimeException("@TestObjectClean must return void");
        if ((method.getModifiers() & Modifier.STATIC) < 1)
            throw new RuntimeException("@TestObjectClean must be statid");

        if (method.getParameterTypes().length == 0 ||
                method.getParameterTypes()[0] != testClass)
           throw new RuntimeException("@TestObjectClean must take an argument of the tested type") ;
        method.setAccessible(true);
        return method;
    }

    private static Object createTestObject(Method creator){
        if (creator != null){
            try{
                return creator.invoke(testClass);
            }catch (Exception e){
                throw new RuntimeException("Couldn't run " + "@TestObject (creator) method : l" + creator.getName());
            }
        }else {
            try{
                return  testClass.newInstance();
            }catch (Exception e){
                throw  new RuntimeException("test object.Try using a @TestObjectCreate method");
            }
        }

    }
}
