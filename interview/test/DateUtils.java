package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateUtils {
    /**
     * yyyyMMdd
     */
    public static final String FORMAT_DATE_UNSIGNED = "yyyyMMdd";
    public static final DateTimeFormatter FORMAT_DATE_SECOND = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String convertTimeToString(long time){
        return FORMAT_DATE_SECOND.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }

    public static Long intToTimeStamp(Integer date) {
        try {
            return new SimpleDateFormat(FORMAT_DATE_UNSIGNED).parse(String.valueOf(date)).getTime();
        } catch (ParseException e) {
            return null;
        }
    }
    static class t{
        public long time ;
    }
    public static void main(String[] args){
        System.out.println(convertTimeToString(new DateUtils.t().time));
    }
}
