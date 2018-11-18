/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.parser;

import java.util.Stack;

/**
 * @author lfjin
 *
 */
public class ExcelFormulaStack {
    private Stack<ExcelFormulaToken> stack = new Stack<ExcelFormulaToken>();

    public ExcelFormulaStack() {
    }

    public void Push(ExcelFormulaToken token) {
        stack.push(token);
    }

    public ExcelFormulaToken pop() {
        if (stack.size() == 0) {
            return null;
        }
        return new ExcelFormulaToken("", stack.pop().getType(), ExcelFormulaTokenSubtype.Stop);
    }

    public ExcelFormulaToken current() {
        return (stack.size() > 0) ? stack.peek() : null;
    }

}
