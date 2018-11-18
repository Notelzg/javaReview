/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.processor;

import cn.com.goldwind.kit.excel.parser.ExcelFormulaToken;
import cn.com.goldwind.kit.excel.parser.ExcelFormulaTokenType;
import cn.com.goldwind.kit.excel.processor.impl.*;

/**
 * @author lfjin
 *
 */
public class ProcessorFactory {
    public static ITokenProcessor getProcessor(ExcelFormulaToken token) {
        ExcelFormulaTokenType type = token.getType();
        ITokenProcessor processor;

        switch (type) {
        case Function:
            processor = new FunctionProcessor();
            break;
        case Operand:
            processor = new ArgumentProcessor();
            break;
        case OperatorInfix:
            processor = new OperatorProcessor();
            break;
        case Subexpression:
            processor = new SubexpressionProcessor();
            break;
        case OperatorPostfix:
            processor = new OperatorPostfixProcessor();
            break;
        default:
            processor = new DefaultProcessor();
            break;
        }

        return processor;
    }
}
