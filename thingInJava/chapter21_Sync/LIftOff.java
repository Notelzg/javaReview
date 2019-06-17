package chapter21_Sync;


public class LIftOff implements  Runnable{
    private int countDown = 3 ;
    private static int taskCount = 0;
    private final  int id = taskCount++;


    public LIftOff() {
        System.out.println("constructure ");
    }
    public LIftOff(int countDown) {
        this.countDown = countDown;
    }

    @Override
    public String toString() {
        return "LIftOff{" +
                "id=  " + id +
                "  countDown=" + countDown +
                '}';
    }

    @Override
    public void run() {
        System.out.println("Run start");
        while (countDown-- > 0){
            System.out.println(this.toString());
            Thread.yield();
        }
        System.out.println("Run ---end ");
    }
    public static void main(String[] args){
        LIftOff lanuch = new LIftOff();
        for (int i = 0; i < 5; i++)
           new Thread(new LIftOff()).start();
    }
}
