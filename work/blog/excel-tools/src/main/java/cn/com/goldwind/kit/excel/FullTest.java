/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;

/**
 * @author lfjin
 *
 */
public class FullTest {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String s = "附表3!$D$25*附表3!$F$80*_xlfn.IFNA(VLOOKUP(附表7!DG1,附表8!$M$3:$N$22,2,FALSE),0)";
        s = "INDEX(附表3.基本假设信息表!$D$89:$AW$95,附表3.基本假设信息表!$C$88,附表4.年度报表!E1-MIN(附表3.基本假设信息表!$E$18:$G$18)+7)";
        ExcelExtract ee = new ExcelExtract(args[0]);
        System.out.println(ee.translateFormula(null, s));
        String f = "a-b<=c";
        // f =
        // "logic.if(AT3>0,0,附表3_D25*附表3_B80*search.vlookup(AT1,2,附表8_M3,附表8_N3,附表8_M4,附表8_N4,附表8_M5,附表8_N5,2,FALSE))";
        Map m = new HashMap<>();
        // m.put("AT3", 0);
        // m.put("AT1", "aaaa");
        // m.put("附表3_D25", 2);
        // m.put("附表3_B80", 3);
        // m.put("附表8_M3", "1111");
        // m.put("附表8_N3", 1);
        // m.put("附表8_M4", "aaaa");
        // m.put("附表8_N4", 5);
        // m.put("附表8_M5", "ccc");
        // m.put("附表8_N5", 4);

        // Object o = AviatorEvaluator.execute(f, m);
        Object o = AviatorEvaluator.exec(f, 2, 1, 0);
        System.out.println(o);
    }

}
