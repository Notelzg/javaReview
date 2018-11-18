/**
 * Copyright 2017 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package cn.com.goldwind.kit.excel.aviator.search;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.aviator.AviatorEvaluator;

import cn.com.goldwind.kit.excel.aviator.function.search.ChooseFunction;
import cn.com.goldwind.kit.excel.aviator.function.search.HLookupFunction;
import cn.com.goldwind.kit.excel.aviator.function.search.IndexFunction;
import cn.com.goldwind.kit.excel.aviator.function.search.VLookupFunction;

/**
 * @author lfjin
 *
 */
public class CustomSearchFunctionTest {
    @BeforeClass
    public static void setup() {
        AviatorEvaluator.addFunction(new ChooseFunction());
        AviatorEvaluator.addFunction(new HLookupFunction());
        AviatorEvaluator.addFunction(new VLookupFunction());
        AviatorEvaluator.addFunction(new IndexFunction());
    }

    @Test
    public void testChoose() {
        String exp1 = "search.choose(a0,a1,a2,a3)";
        assertEquals("a", AviatorEvaluator.exec(exp1, 1, "a", "b", "c"));
    }

    @Test
    public void testHLookup() {
        String exp1 = "search.hlookup(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15, a16)";
        assertEquals(4, AviatorEvaluator.exec(exp1, "车轴", 3, "车轴", "轴承", "螺栓", 4, 4, 9, 5, 7, 10, 6, 8, 11, 2, false));
        assertEquals(7, AviatorEvaluator.exec(exp1, "轴承", 3, "车轴", "轴承", "螺栓", 4, 4, 9, 5, 7, 10, 6, 8, 11, 3, false));
        assertEquals(5, AviatorEvaluator.exec(exp1, 4, 3, 4.0, 5, 6.0, 4, 4, 9, 5, 7, 10, 6, 8, 11, 3, false));
        exp1 = "search.hlookup('车轴',a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15, a16)";
        assertEquals(4, AviatorEvaluator.exec(exp1, 3, "车轴", "轴承", "螺栓", 4, 4, 9, 5, 7, 10, 6, 8, 11, 2, false));
    }

    @Test
    public void testVLookup() {
        String exp1 = "search.vlookup(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20,a21,a22,a23,a24,a25,a26,a27,a28,a29,a30,a31,a32,a33,a34)";
        assertEquals(3.25,
                     AviatorEvaluator.exec(exp1, 0.525, 3, "密度", "粘度", "温度", 0.457, 3.55, 500, 0.525, 3.25, 400, 0.606,
                                           2.93, 300, 0.675, 2.75, 250, 0.746, 2.57, 200, 0.835, 2.38, 150, 0.946, 2.17,
                                           100, 1.09, 1.95, 50, 1.29, 1.71, 0, 2, false));
        assertEquals(300,
                     AviatorEvaluator.exec(exp1, 0.606, 3, "密度", "粘度", "温度", 0.457, 3.55, 500, 0.525, 3.25, 400, 0.606,
                                           2.93, 300, 0.675, 2.75, 250, 0.746, 2.57, 200, 0.835, 2.38, 150, 0.946, 2.17,
                                           100, 1.09, 1.95, 50, 1.29, 1.71, 0, 3, false));
        assertEquals(300,
                     AviatorEvaluator.exec(exp1, 6.0, 3, "密度", "粘度", "温度", 0.457, 3.55, 500, 0.525, 3.25, 400, 6, 2.93,
                                           300, 0.675, 2.75, 250, 0.746, 2.57, 200, 0.835, 2.38, 150, 0.946, 2.17, 100,
                                           1.09, 1.95, 50, 1.29, 1.71, 0, 3, false));
        exp1 = "search.vlookup(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20,a21,a22,a23,a24,a25,a26,a27,a28,a29,a30,a31,a32,a33)";
        assertEquals(300,
                     AviatorEvaluator.exec(exp1, 6.0, 3, "密度", "粘度", "温度", 0.457, 3.55, 500, 0.525, 3.25, 400, 6, 2.93,
                                           300, 0.675, 2.75, 250, 0.746, 2.57, 200, 0.835, 2.38, 150, 0.946, 2.17, 100,
                                           1.09, 1.95, 50, 1.29, 1.71, 0, 3));
    }

    @Test
    public void testIndex() {
        String exp = "search.index(a0,a1,a2,a3,a4,a5,a6)";
        assertEquals("梨", AviatorEvaluator.exec(exp, 2, "苹果", "柠檬", "香蕉", "梨", 2, 2));
        assertEquals("香蕉", AviatorEvaluator.exec(exp, 2, "苹果", "柠檬", "香蕉", "梨", 2, 1));
    }
}
