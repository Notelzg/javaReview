/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.date;

import java.text.SimpleDateFormat;

/**
 * @author lfjin
 *
 */
public class DateFormat {
    public static String DATE_FORMAT           = "yyyy-MM-dd";
    public static String FULL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss:SS";

    public static java.text.DateFormat getDateFormatter() {
        return new SimpleDateFormat(DATE_FORMAT);
    }

    public static java.text.DateFormat getFullDatetimeFormatter() {
        return new SimpleDateFormat(FULL_DATE_TIME_FORMAT);
    }
}
