package chapter20_Annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
public class UseCaseTracker {
    public static void trackUseCase(List<Integer> usrACase, Class<?> cl){
        for (Method method : cl.getDeclaredMethods()){
            UseCase useCase =  method.getAnnotation(UseCase.class);
            if (useCase != null){
                System.out.println(useCase.id() + "descpriton : " + useCase.descriptin());
                usrACase.remove(new Integer(useCase.id()));
            }
            for (int i : usrACase){
                System.out.println("Waring not find UaseCase" + i);
            }
        }
    }
    public static  void main(String[] args){
        List<Integer> useCases = new ArrayList<>();
        Collections.addAll(useCases, 47, 48 , 49);
        trackUseCase(useCases, PasswordUtils.class);
    }
}
