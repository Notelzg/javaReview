/**
 * Copyright 2017 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package cn.com.goldwind.kit.excel.aviator.logic;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.aviator.AviatorEvaluator;

import cn.com.goldwind.kit.excel.aviator.function.logic.AndFunction;
import cn.com.goldwind.kit.excel.aviator.function.logic.IfFunction;
import cn.com.goldwind.kit.excel.aviator.function.logic.OrFunction;

/**
 * @author lfjin
 *
 */
public class CustomLogicFunctionTest {
    @BeforeClass
    public static void setup() {
        AviatorEvaluator.addFunction(new AndFunction());
        AviatorEvaluator.addFunction(new IfFunction());
        AviatorEvaluator.addFunction(new OrFunction());
    }

    @Test
    public void testAnd() {
        String exp1 = "logic.and(x>5, y<5)";
        String exp2 = "logic.and(x>5, y<5, z==0)";
        assertEquals(true, AviatorEvaluator.exec(exp1, 10, 3));
        assertEquals(false, AviatorEvaluator.exec(exp1, 10, 6));
        assertEquals(false, AviatorEvaluator.exec(exp2, 0, 3, 0));
        assertEquals(true, AviatorEvaluator.exec(exp2, 10, 3, 0));
        assertEquals(false, AviatorEvaluator.exec(exp2, 10, 3, 1));
    }

    @Test
    public void testOr() {
        String exp1 = "logic.or(x>5, y<5)";
        String exp2 = "logic.or(x>5, y<5, z==0)";
        assertEquals(true, AviatorEvaluator.exec(exp1, 10, 3));
        assertEquals(true, AviatorEvaluator.exec(exp1, 10, 6));
        assertEquals(true, AviatorEvaluator.exec(exp2, 0, 3, 0));
        assertEquals(false, AviatorEvaluator.exec(exp2, 1, 13, 10));
        assertEquals(true, AviatorEvaluator.exec(exp2, 10, 3, 1));
    }

    @Test
    public void testIf() {
        String exp1 = "logic.if(x>5, 2, 1)";
        String exp2 = "logic.if(logic.or(x>5, y<5), 2, 1)";
        assertEquals(2, AviatorEvaluator.exec(exp1, 10));
        assertEquals(1, AviatorEvaluator.exec(exp1, 3));
        assertEquals(2, AviatorEvaluator.exec(exp2, 10, 3));
        assertEquals(1, AviatorEvaluator.exec(exp2, 2, 6));
        String f = "logic.if(x>5, 2, '')";
        assertEquals("", AviatorEvaluator.exec(f, 2));
    }

}
