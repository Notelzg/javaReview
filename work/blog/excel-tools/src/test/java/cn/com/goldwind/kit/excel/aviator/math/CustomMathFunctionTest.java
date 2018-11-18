/**
 * Copyright 2017 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package cn.com.goldwind.kit.excel.aviator.math;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.aviator.AviatorEvaluator;

import cn.com.goldwind.kit.excel.aviator.function.math.AverageFunction;
import cn.com.goldwind.kit.excel.aviator.function.math.IntFunction;
import cn.com.goldwind.kit.excel.aviator.function.math.MaxFunction;
import cn.com.goldwind.kit.excel.aviator.function.math.MinFunction;
import cn.com.goldwind.kit.excel.aviator.function.math.RoundDownFunction;
import cn.com.goldwind.kit.excel.aviator.function.math.RoundFunction;
import cn.com.goldwind.kit.excel.aviator.function.math.RoundUpFunction;
import cn.com.goldwind.kit.excel.aviator.function.math.SumFunction;

/**
 * @author lfjin
 *
 */
public class CustomMathFunctionTest {
    @BeforeClass
    public static void setup() {
        AviatorEvaluator.addFunction(new AverageFunction());
        AviatorEvaluator.addFunction(new IntFunction());
        AviatorEvaluator.addFunction(new MaxFunction());
        AviatorEvaluator.addFunction(new MinFunction());
        AviatorEvaluator.addFunction(new RoundDownFunction());
        AviatorEvaluator.addFunction(new RoundFunction());
        AviatorEvaluator.addFunction(new RoundUpFunction());
        AviatorEvaluator.addFunction(new SumFunction());
    }

    @Test
    public void testAvg() {
        String exp1 = "math.avg(x)";
        String exp2 = "math.avg(x, y, z)";
        int[] y = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List z = new ArrayList();
        // Map m = new HashMap();
        // m.put("a", 1);
        // // m.put("b", "");
        // assertEquals(0, AviatorEvaluator.execute("a+b", m));
        // assertEquals(0, AviatorEvaluator.execute("a-b", m));
        // assertEquals(0, AviatorEvaluator.execute("a*b", m));
        // assertEquals(0, AviatorEvaluator.execute("a/b", m));
        assertEquals(5.5, AviatorEvaluator.exec(exp1, y));
        assertEquals(65 / 11, AviatorEvaluator.exec(exp2, 10, y, z));
        assertEquals(55 / 10f, AviatorEvaluator.exec(exp2, "", y, z));

        double[] DH13s = {2362.566091, 2362.566091, 2362.566091, 2362.566091, 2362.566091, 2362.566091, 2479.06213,
                2608.361613, 2615.155932, 2621.950251, 2628.74457, 2634.972696, 2634.972696, 2634.972696, 2634.972696,
                2634.972696, 2634.972696, 2634.972696, 2634.972696, 2634.972696};
        double[] DH14s = {1169.022547, 1159.280692, 1042.378438, 925.476183, 808.5739283, 691.6716736, 574.7694189,
                457.8671642, 340.9649095, 224.0626548, 107.1604001, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        double[] rrr = {0, 0, 0, 179.6362385, 194.2490203, 208.8618021, 476.0731778, 537.6236121, 568.5477556,
                599.471899, 630.3960425, 658.743174, 658.743174, 658.743174, 658.743174, 658.743174, 658.743174,
                658.743174, 658.743174, 658.743174};
        String ttt = "DH13-DH14>0?(y<=J18?0:y<=J18+J19?(DH13-DH14)*DH3/2:(DH13-DH14)*DH3):0";
        Map m = new HashMap();
        m.put("J18", 3);
        m.put("J19", 3);
        m.put("DH3", 0.25);
        for (int i = 0; i < DH14s.length; i++) {
            m.put("y", i + 1);
            m.put("DH14", DH14s[i]);
            m.put("DH13", DH13s[i]);
            assertEquals(rrr[i], AviatorEvaluator.execute(ttt, m));
        }
    }

    @Test
    public void testInt() {
        String exp = "math.int(arg)";
        assertEquals(8, AviatorEvaluator.exec(exp, 8.9));
        assertEquals(-9, AviatorEvaluator.exec(exp, -8.9));
    }

    @Test
    public void testMin() {
        String exp1 = "math.min(arg1, arg2)";
        String exp2 = "math.min(arg1, arg2, arg3)";
        assertEquals(2, AviatorEvaluator.exec(exp1, 6, 2));
        assertEquals(2.011f, AviatorEvaluator.exec(exp1, 6.000111f, 2.011f));
        assertEquals(2, AviatorEvaluator.exec(exp2, 6, 2, 8));
        assertEquals(6, AviatorEvaluator.exec(exp2, 6, "10", ""));
        assertEquals(2, AviatorEvaluator.exec(exp2, 6, 2, null));
    }

    @Test
    public void testMax() {
        String exp1 = "math.max(arg1, arg2)";
        String exp2 = "math.max(arg1, arg2, arg3)";
        Map m = new HashMap();
        m.put("arg1", 6);
        m.put("arg2", 2);
        assertEquals(6, AviatorEvaluator.execute(exp1, m));
        assertEquals(6, AviatorEvaluator.exec(exp1, 6, 2));
        assertEquals(6.000111f, AviatorEvaluator.exec(exp1, 6.000111f, 2.011f));
        assertEquals(8, AviatorEvaluator.exec(exp2, 6, 2, 8));
        assertEquals(10, AviatorEvaluator.exec(exp2, 6, "10", ""));
        assertEquals(6, AviatorEvaluator.exec(exp2, 6, 2, null));

    }

    @Test
    public void testRoundDown() {
        String exp = "math.rounddown(arg, arg1)";

        assertEquals(3f, AviatorEvaluator.exec(exp, 3.2, 0));
        // �� 3.2 �������뵽���С��λ���� 3
        assertEquals(76f, AviatorEvaluator.exec(exp, 76.9, 0));
        // �� 76.9 �������뵽���С��λ���� 76
        assertEquals(3.141f, AviatorEvaluator.exec(exp, 3.14159, 3));
        // �� 3.14159 �������뵽����С��λ���� 3.141
        assertEquals(-3.1f, AviatorEvaluator.exec(exp, -3.14159, 1));
        // �� -3.14159 �������뵽һ��С��λ���� -3.1
        assertEquals(31400f, AviatorEvaluator.exec(exp, 31415.92654, -2));
        // �� 31415.92654 �������뵽С���������λ���� 31400
    }

    @Test
    public void testRound() {
        String exp = "math.newround(arg, arg1)";

        assertEquals(2.2f, AviatorEvaluator.exec(exp, 2.15, 1));
        // �� 2.15 �������뵽һ��С��λ 2.2
        assertEquals(2.1f, AviatorEvaluator.exec(exp, 2.149, 1));
        // �� 2.149 �������뵽һ��С��λ 2.1
        assertEquals(-1.48f, AviatorEvaluator.exec(exp, -1.475, 2));
        // �� -1.475 �������뵽����С��λ -1.48
        assertEquals(20f, AviatorEvaluator.exec(exp, 21.5, -1));
        // �� 21.5 �������뵽С�������һλ 20
        assertEquals(1000f, AviatorEvaluator.exec(exp, 626.3, -3));
        // �� 626.3 ��������Ϊ��ӽ��� 1000 �ı��� 1000
        assertEquals(0f, AviatorEvaluator.exec(exp, 1.98, -1));
        // �� 1.98 ��������Ϊ��ӽ��� 10 �ı��� 0
        assertEquals(-100f, AviatorEvaluator.exec(exp, -50.55, -2));
        // �� -50.55 ��������Ϊ��ӽ��� 100 �ı��� -100

    }

    @Test
    public void testRoundUp() {
        String exp = "math.roundup(arg, arg2)";

        assertEquals(4, AviatorEvaluator.exec(exp, 3.2, 0));
        // �� 3.2 �������뵽���С��λ���� 4
        assertEquals(77, AviatorEvaluator.exec(exp, 76.9, 0));
        // �� 76.9 �������뵽���С��λ���� 77
        assertEquals(3.142, AviatorEvaluator.exec(exp, 3.14159, 3));
        // �� 3.14159 �������뵽����С��λ���� 3.142
        assertEquals(-3.2, AviatorEvaluator.exec(exp, -3.14159, 1));
        // �� -3.14159 �������뵽һ��С��λ���� -3.2
        assertEquals(31500, AviatorEvaluator.exec(exp, 31415.92654, -2));
        // �� 31415.92654 �������뵽С���������λ���� 31500
    }

    @Test
    public void testSum() {
        String exp1 = "math.sum(x)";
        String exp2 = "math.sum(x, y, z)";
        int[] y = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List z = new ArrayList();
        assertEquals(0, AviatorEvaluator.exec(exp1, z));
        assertEquals(55, AviatorEvaluator.exec(exp1, y));
        assertEquals(65, AviatorEvaluator.exec(exp2, 10, y, z));
        assertEquals(6, AviatorEvaluator.exec(exp2, "2", "", "4"));
    }

}
