package chapter20_Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class SqlAnnotation {

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DBTable{
        public String name() default "";
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Constraints{
        boolean primaryKey() default  false;
        boolean allowNull() default  true;
        boolean unique() default  false;
    }


    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SqlString{
        public String name() default "";
        int value() default  0;
        Constraints constraints() default  @Constraints;
    }


    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SqlInteger{
        int value() default 0;
        String name() default "";
        Constraints constraints() default @Constraints;
    }
}
