/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.statistics;

import cn.com.goldwind.kit.excel.aviator.function.statistics.CountifFunction;
import com.googlecode.aviator.AviatorEvaluator;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author lfjin
 *
 */
public class CustomStatisticsFunctionTest {
    @BeforeClass
    public static void setup() {

        AviatorEvaluator.addFunction(new CountifFunction());
    }

    @Test
    public void testContif() {
        assertEquals(2, AviatorEvaluator.exec("statistics.countif(a0,a1,a2,a3,a4,a5,a6,a7)", 1, 2, 2, 3, 4, 5, 5, 2));
        assertEquals(5, AviatorEvaluator.exec("statistics.countif(a0,a1,a2,a3,a4,a5,a6,a7)", 1, 2, 2, 3, 4, 5, 5, "<>2"));
        assertEquals(4,
                     AviatorEvaluator.exec("statistics.countif(a0,a1,a2,a3,a4,a5,a6,a7)", 1, 2, 2, 3, 4, 5, 5, ">2"));
        assertEquals(1, AviatorEvaluator.exec("statistics.countif(a0,a1,a2,a3)", "苹果", "梨", "桔", "苹果"));
    }
}
