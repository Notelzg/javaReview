/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.processor.impl;

import cn.com.goldwind.kit.excel.parser.ExcelFormulaParser;
import cn.com.goldwind.kit.excel.parser.ExcelFormulaToken;
import cn.com.goldwind.kit.excel.parser.ExcelFormulaTokenSubtype;
import cn.com.goldwind.kit.excel.processor.ITokenProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lfjin
 *
 */
public class FunctionProcessor implements ITokenProcessor {
    static Map<String, String> FUNCTION_TRASNLATE = new HashMap<>();

    static {
        FUNCTION_TRASNLATE.put("SUM", "math.sum");
        FUNCTION_TRASNLATE.put("IF", "logic.if");
        FUNCTION_TRASNLATE.put("AVERAGE", "math.avg");
        FUNCTION_TRASNLATE.put("ROUND", "math.newround");
        FUNCTION_TRASNLATE.put("AND", "logic.and");
        FUNCTION_TRASNLATE.put("CHOOSE", "search.choose");
        FUNCTION_TRASNLATE.put("HLOOKUP", "search.hlookup");
        FUNCTION_TRASNLATE.put("PPMT", "finance.ppmt");
        FUNCTION_TRASNLATE.put("PMT", "finance.pmt");
        FUNCTION_TRASNLATE.put("MIN", "math.min");
        FUNCTION_TRASNLATE.put("EOMONTH", "date.eomonth");
        FUNCTION_TRASNLATE.put("YEAR", "date.year");
        FUNCTION_TRASNLATE.put("INT", "math.int");
        FUNCTION_TRASNLATE.put("VLOOKUP", "search.vlookup");
        FUNCTION_TRASNLATE.put("MAX", "math.max");
        FUNCTION_TRASNLATE.put("ROUNDDOWN", "math.rounddown");
        FUNCTION_TRASNLATE.put("DATE", "date.date");
        FUNCTION_TRASNLATE.put("ROUNDUP", "math.roundup");
        FUNCTION_TRASNLATE.put("OR", "logic.or");
        FUNCTION_TRASNLATE.put("IRR", "finance.irr");
        FUNCTION_TRASNLATE.put("COUNTIF", "statistics.countif");
        FUNCTION_TRASNLATE.put("MONTH", "date.month");
        FUNCTION_TRASNLATE.put("XIRR", "finance.xirr");
        FUNCTION_TRASNLATE.put("XNPV", "finance.xnpv");
        FUNCTION_TRASNLATE.put("NPV", "finance.npv");
        FUNCTION_TRASNLATE.put("CONCATENATE", "string.concatenate");
        FUNCTION_TRASNLATE.put("_XLFN.IFNA", "logic.ifna");
        FUNCTION_TRASNLATE.put("SUMIF", "math.sumif");
        FUNCTION_TRASNLATE.put("SUMIFS", "math.sumifs");
        FUNCTION_TRASNLATE.put("INDEX", "search.index");
    }

    /*
     * (non-Javadoc)
     *
     * @see cn.com.goldwind.kit.excel.processor.ITokenProcess#process()
     */
    @Override
    public String process(ExcelFormulaToken token) {
        if (ExcelFormulaTokenSubtype.Start == token.getSubtype()) {
            String rtn = token.getValue();
            String name = rtn.toUpperCase();
            if (FUNCTION_TRASNLATE.containsKey(name)) {
                rtn = FUNCTION_TRASNLATE.get(name);
            }
            return rtn + ExcelFormulaParser.PAREN_OPEN;
        } else if (ExcelFormulaTokenSubtype.Stop == token.getSubtype()) {
            return String.valueOf(ExcelFormulaParser.PAREN_CLOSE);
        }
        return "";
    }

}
