/**
 * Copyright 2017 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package cn.com.goldwind.kit.excel.aviator.date;

import cn.com.goldwind.kit.excel.aviator.function.date.DateFunction;
import cn.com.goldwind.kit.excel.aviator.function.date.EomonthFunction;
import cn.com.goldwind.kit.excel.aviator.function.date.MonthFunction;
import cn.com.goldwind.kit.excel.aviator.function.date.YearFunction;
import com.googlecode.aviator.AviatorEvaluator;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author lfjin
 *
 */
public class CustomDateFunctionTest {
    @BeforeClass
    public static void setup() {
        AviatorEvaluator.addFunction(new DateFunction());
        AviatorEvaluator.addFunction(new EomonthFunction());
        AviatorEvaluator.addFunction(new MonthFunction());
        AviatorEvaluator.addFunction(new YearFunction());
    }

    @Test
    public void testDate() {
        String exp1 = "date.date(x,y,z)";
        assertEquals("2018-03-12", AviatorEvaluator.exec(exp1, 2018, 3, 12));
    }

    @Test
    public void testEomonth() {
        String exp1 = "date.eomonth(x,y)";
        assertEquals("2011-02-28", AviatorEvaluator.exec(exp1, "2011-01-01 00:00:00:00", 1));
        assertEquals("2010-10-31", AviatorEvaluator.exec(exp1, "2011-01-01", -3));

    }

    @Test
    public void testMonth() {
        String exp1 = "date.month(x)";
        assertEquals(1, AviatorEvaluator.exec(exp1, "2011-01-01"));
        assertEquals(2, AviatorEvaluator.exec(exp1, "2011-02-01"));

    }

    @Test
    public void testYear() {
        String exp1 = "date.year(x)";
        assertEquals(2011, AviatorEvaluator.exec(exp1, "2011-01-01"));
        assertEquals(2010, AviatorEvaluator.exec(exp1, "2010-01-01"));
    }
}
