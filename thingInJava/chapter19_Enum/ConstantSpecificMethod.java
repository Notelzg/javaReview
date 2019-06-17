package chapter19_Enum;

import javafx.scene.input.DataFormat;

import java.util.EnumSet;
import java.util.Iterator;
interface command{
    String getInfo();
}
public enum ConstantSpecificMethod {
    DATE_TIME {
        String getInfo() {
            return "2019-06009";
        }
    },
    CLASSPATH {
        String getInfo() {
            return System.getenv("CLASSPATH");
        }
    },
    VERSION {
        String getInfo() {
            return System.getProperty("java.version");
        }
        String test(){
            return "testA;";
        }
    };
    abstract String getInfo();
    enum  t   implements command {
        TEST {
            public String getInfo(){
                return "test";
            }
        };
    };
    public static void main(String[] args){
        for (ConstantSpecificMethod csm : values()){
            System.out.println(csm.getInfo());
        }
        System.out.println(VERSION.getInfo());
        System.out.println(VERSION.ordinal());
    }
}
