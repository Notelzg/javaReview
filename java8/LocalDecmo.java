import java.sql.Timestamp;
import java.time.LocalDate;

public class LocalDecmo {
    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        System.out.println(now);
        Timestamp timestamp = Timestamp.valueOf(now.atStartOfDay());
        System.out.println(timestamp);
        System.out.println(timestamp.getTime());

    }
}
