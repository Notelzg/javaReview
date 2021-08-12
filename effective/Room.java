import sun.misc.Cleaner;

public class Room implements AutoCloseable {
    private final Cleaner cleaner ;

    private static class State implements Runnable{
        int numJunPiles ;

        public State(int numJunPiles) {
            this.numJunPiles = numJunPiles;
        }

        @Override
        public void run() {
            System.out.println("Cleaner romm");
            numJunPiles = 0;
        }
    }
    private final  State state;


    public Room(int num) {
        this.state = new State(num);
        cleaner = Cleaner.create(num, state);
    }

    @Override
    public void close() throws Exception {
        cleaner.clean();
    }
}
