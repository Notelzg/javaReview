public class Adult {
    public static void main(String[] args) throws Exception {
        try (Room rom = new Room(7)) {
            System.out.println("goog byle");
            Object obj = new Object();
        }
    }
}