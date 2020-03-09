package level_easy;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Sqrt {
    public int mySqrt(int x) {
        if (x <= 0)
            return 0;
       return  (int )Math.sqrt(x) ;
    }

    @Test
    public void test(){
        Assertions.assertEquals(2, mySqrt(4));
        Assertions.assertEquals(2, mySqrt(8));
    }
}
