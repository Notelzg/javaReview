/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.processor.impl;

import cn.com.goldwind.kit.excel.parser.ExcelFormulaToken;
import cn.com.goldwind.kit.excel.parser.ExcelFormulaTokenSubtype;
import cn.com.goldwind.kit.excel.processor.ITokenProcessor;

/**
 * @author lfjin
 *
 */
public class SubexpressionProcessor implements ITokenProcessor {
    /*
     * (non-Javadoc)
     *
     * @see cn.com.goldwind.kit.excel.processor.ITokenProcess#process()
     */
    @Override
    public String process(ExcelFormulaToken token) {
        ExcelFormulaTokenSubtype subtype = token.getSubtype();
        switch (subtype) {
        case Start:
            return "(";
        default:
            return ")";
        }
    }

}
