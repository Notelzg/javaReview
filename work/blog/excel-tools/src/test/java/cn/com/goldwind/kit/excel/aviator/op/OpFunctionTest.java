/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.op;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.lexer.token.OperatorType;

/**
 * @author lfjin
 *
 */
public class OpFunctionTest {
    @BeforeClass
    public static void setup() {
        AviatorEvaluator.addOpFunction(OperatorType.ADD, new AddOpFunction());
        AviatorEvaluator.addOpFunction(OperatorType.SUB, new MinusOpFunction());
        AviatorEvaluator.addOpFunction(OperatorType.BIT_XOR, new PowerOpFunction());
    }

    public void test() {
        String exp1 = "x-y+1";
        assertEquals(642, AviatorEvaluator.exec(exp1, "2020-12-31 00:00:00:00", "2019-03-31 00:00:00:00"));
        assertEquals(642, AviatorEvaluator.exec(exp1, "2020-12-31", "2019-03-31 00:00:00:00"));
        assertEquals(642, AviatorEvaluator.exec(exp1, "2020-12-31 00:00:00:00", "2019-03-31"));
    }

    @Test
    public void testPower() {
        String exp = "x^0.25*y";
        System.out.println(AviatorEvaluator.exec(exp, 1.05, 1.0122722344290394));
        System.out.println(Math.pow(1.05, 0.25) * 1.0122722344290394);
    }
}
