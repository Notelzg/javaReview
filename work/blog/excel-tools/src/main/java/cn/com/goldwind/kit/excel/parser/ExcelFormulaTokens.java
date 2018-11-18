/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lfjin
 *
 */
public class ExcelFormulaTokens {

    private int                     index = -1;
    private List<ExcelFormulaToken> tokens;

    public ExcelFormulaTokens() {
        this(4);
    }

    public ExcelFormulaTokens(int capacity) {
        tokens = new ArrayList<ExcelFormulaToken>(capacity);
    }

    public int count() {
        return tokens.size();
    }

    public boolean BOF() {
        return (index <= 0);
    }

    public boolean EOF() {
        return (index >= (tokens.size() - 1));
    }

    public ExcelFormulaToken current() {

        if (index == -1) {
            return null;
        }
        return tokens.get(index);

    }

    public ExcelFormulaToken next() {

        if (EOF()) {
            return null;
        }
        return tokens.get(index + 1);
    }

    public ExcelFormulaToken previous() {
        if (index < 1) {
            return null;
        }
        return tokens.get(index - 1);

    }

    public ExcelFormulaToken add(ExcelFormulaToken token) {
        tokens.add(token);
        return token;
    }

    public boolean moveNext() {
        if (EOF()) {
            return false;
        }
        index++;
        return true;
    }

    public void reset() {
        index = -1;
    }

}
