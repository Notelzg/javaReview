/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.processor;

import cn.com.goldwind.kit.excel.parser.ExcelFormulaToken;

/**
 * @author lfjin
 *
 */
public interface ITokenProcessor {
    public String process(ExcelFormulaToken token);
}
