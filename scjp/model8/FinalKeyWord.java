import java.io.*;
public class FinalKeyWord{

    private static final FinalKeyWord finalKeyWord = new FinalKeyWord();
    public static FinalKeyWord getSingleInstance() {
        return FinalKeyWord.finalKeyWord;
    }
    public FinalKeyWord(){
        intTest = 14;
    };

    public FinalKeyWord(int test){
        intTest = test;
    };
    public static final long longId = 12L;

    private final int intTest;
    public int createIntTest(){
        return 12;
    }

    public int getIntTest(){
        return this.intTest;
    }
}
