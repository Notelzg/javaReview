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

import java.text.DecimalFormat;
import java.text.NumberFormat;

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
        String exp3 = "logic.if(附表7辅助计算表_DW2-附表8电价限电预测表_N15<=(附表3基本假设信息表_B121+附表3基本假设信息表_B122)*4.0,0,1)";
        Object o = AviatorEvaluator.exec(exp3, 123.0, 2.0, 1.0, 14.0);
        exp3 = "a";
        NumberFormat formatter = new DecimalFormat("#0.0000000000");  /* 精度是10*/
        o = AviatorEvaluator.exec(exp3, 8.615330671091203E-17);
                /*  输出结果是 1 ，也存在问题 */
        formatter.format(Double.parseDouble(o.toString()));
        System.out.println(formatter.format(Double.parseDouble(o.toString())));
        String s1 = "-0.0000000000";
        String s2 = "0.0000000000";
        if (s1.endsWith("0.0000000000") && s2.endsWith("0.0000000000")){
            System.out.println("================");
        }
        System.out.println(s1.endsWith(""));
        System.out.println(formatter.format(Double.parseDouble(" -2.87481149996438E-15")));
    }

}
