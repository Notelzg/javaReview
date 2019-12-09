package performance;

public class TestParam {
    private int size;
    private int loop;


    public TestParam() {
    }

    public TestParam(int size, int loop) {
        this.size = size;
        this.loop = loop;
    }
   public static  TestParam[] array(int... values){
        int size = values.length/2;
        TestParam[] rs = new TestParam[size];
        int n = 0;
        for (int i = 0; i < size; i++){
            TestParam temp = new TestParam(values[n++], values[n++]);
            rs[i] = temp;
        }
        return rs;
   }

   public static TestParam[] array(String[] args){
        int[] vals = new int[args.length];
        for (int i = 0; i < args.length; i++){
            vals[i] = Integer.decode(args[i]);
        }
        return array(vals);
   }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getLoop() {
        return loop;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }
}
