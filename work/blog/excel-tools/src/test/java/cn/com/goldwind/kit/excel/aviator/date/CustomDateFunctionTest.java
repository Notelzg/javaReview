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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;

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
        NumberFormat formatter = new DecimalFormat("#0.00000         ");  /* 精度是10*/
        exp1 = "finance.irr(a1, a2, a3, a4,a5, a6, a7, a8, a9, a10, a11)";
        Object rs = AviatorEvaluator.exec(exp1,
                -25412.85826724140
                ,-22452.7937659
                ,-4304.89067023433
                ,10864.65902085110
                ,2970.41195320763
                ,9010.23303163240
                ,0, 0, 0, 0, 0);
        System.out.println(rs);
//        (exp1,  -27479.84481896550,-24743.57726846920,-4732.40453441121,11953.75118223910,3269.73046428756,9685.55361816357,0,0,0,0,0);

//        -27479.84481896550,-24743.57726846920,-4732.40453441121,11953.75118223910,3269.73046428756,9685.55361816357, -   	 -   	 -   	 -   	 -
        String orignalFilename = "附件9.金风投资盈利预测模型-20180915.xlsx";
        int begin = orignalFilename.lastIndexOf(".");
        String substring =  orignalFilename.substring(begin);
        System.out.println(substring);

    }

}
