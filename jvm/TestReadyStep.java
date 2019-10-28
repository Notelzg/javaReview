/**
 * 用来测试，class文件在链接阶段的，准备阶段中
 * 如何处理成员变量，对于不同访问标志的
 */
public class TestReadyStep {
    public int a;
    public static int b;
    public static final  int c = 0;
    public final  int d;
    public TestReadyStep(){
        a = 2;
        b = 3;
        d = 1;
    }
}
