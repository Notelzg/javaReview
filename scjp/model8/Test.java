import java.io.*;
public class Test{

    public static void main(String[] args) {
        FinalKeyWord test1 = new FinalKeyWord(112);
        FinalKeyWord test2 = new FinalKeyWord();
        FinalKeyWord test3 =  FinalKeyWord.getSingleInstance();
        System.out.println(test1.longId); 
        System.out.println(test2.longId); 
        System.out.println(test3.longId); 
        System.out.println(test1.getIntTest());
        System.out.println(test2.getIntTest());
        System.out.println(test3.getIntTest());
    }

}
