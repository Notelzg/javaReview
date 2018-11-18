/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author lfjin
 *
 */
public class ExcelFormulaParser {

    public static char              QUOTE_DOUBLE      = '"';
    public static char              QUOTE_SINGLE      = '\'';
    public static char              BRACKET_CLOSE     = ']';
    public static char              BRACKET_OPEN      = '[';
    public static char              BRACE_OPEN        = '{';
    public static char              BRACE_CLOSE       = '}';
    public static char              PAREN_OPEN        = '(';
    public static char              PAREN_CLOSE       = ')';
    public static char              SEMICOLON         = ';';
    public static char              WHITESPACE        = ' ';
    public static char              COMMA             = ',';
    public static char              ERROR_START       = '#';

    static String                   OPERATORS_SN      = "+-";
    static String                   OPERATORS_INFIX   = "+-*/^&=><";
    static String                   OPERATORS_POSTFIX = "%";

    static String[]                 ERRORS            = new String[] {"#NULL!", "#DIV/0!", "#VALUE!", "#REF!", "#NAME?",
            "#NUM!", "#N/A"};

    static String[]                 COMPARATORS_MULTI = new String[] {">=", "<=", "<>"};

    private String                  formula;
    private List<ExcelFormulaToken> tokens;

    /**
     * @return the formula
     */
    public String getFormula() {
        return formula;
    }

    /**
     * @param formula
     *            the formula to set
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }

    /**
     * @return the tokens
     */
    public List<ExcelFormulaToken> getTokens() {
        return tokens;
    }

    /**
     * @param tokens
     *            the tokens to set
     */
    public void setTokens(List<ExcelFormulaToken> tokens) {
        this.tokens = tokens;
    }

    public ExcelFormulaParser(String formula) {
        if (formula == null) {
            throw new IllegalArgumentException("formula is not null");
        }
        this.formula = formula.trim();
        tokens = new ArrayList<ExcelFormulaToken>();
        parseToTokens();
    }

    public ExcelFormulaToken get(int index) {
        return tokens.get(index);
    }

    public int indexOf(ExcelFormulaToken item) {
        return tokens.indexOf(item);
    }

    public boolean contains(ExcelFormulaToken item) {
        return tokens.contains(item);
    }

    public boolean isReadOnly() {
        return true;
    }

    public void copyTo(ExcelFormulaToken[] array, int arrayIndex) {
        List<ExcelFormulaToken> newList = Arrays.asList(array);
        tokens.addAll(arrayIndex, newList);
    }

    public int count() {
        return tokens.size();
    }

    public Iterator<ExcelFormulaToken> iterator() {
        return tokens.iterator();
    }

    private void parseToTokens() {

        // No attempt is made to verify formulas; assumes formulas are derived
        // from Excel, where
        // they can only exist if valid; stack overflows/underflows sunk as
        // nulls without exceptions.

        if ((formula.length() < 2) || !formula.startsWith("=")) {
            // return;
        }

        ExcelFormulaTokens tokens1 = new ExcelFormulaTokens();
        ExcelFormulaStack stack = new ExcelFormulaStack();

        boolean inString = false;
        boolean inPath = false;
        boolean inRange = false;
        boolean inError = false;

        int index = 0;
        String value = "";

        while (index < formula.length()) {

            // state-dependent character evaluation (order is important)

            // double-quoted strings
            // embeds are doubled
            // end marks token

            if (inString) {
                if (formula.charAt(index) == QUOTE_DOUBLE) {
                    if (((index + 2) <= formula.length()) && (formula.charAt(index + 1) == QUOTE_DOUBLE)) {
                        value += QUOTE_DOUBLE;
                        index++;
                    } else {
                        inString = false;
                        tokens1.add(new ExcelFormulaToken(value, ExcelFormulaTokenType.Operand,
                                                          ExcelFormulaTokenSubtype.Text));
                        value = "";
                    }
                } else {
                    value += formula.charAt(index);
                }
                index++;
                continue;
            }

            // single-quoted strings (links)
            // embeds are double
            // end does not mark a token

            if (inPath) {
                if (formula.charAt(index) == QUOTE_SINGLE) {
                    if (((index + 2) <= formula.length()) && (formula.charAt(index + 1) == QUOTE_SINGLE)) {
                        value += QUOTE_SINGLE;
                        index++;
                    } else {
                        inPath = false;
                    }
                } else {
                    value += formula.charAt(index);
                }
                index++;
                continue;
            }

            // bracked strings (R1C1 range index or linked workbook name)
            // no embeds (changed to "()" by Excel)
            // end does not mark a token

            if (inRange) {
                if (formula.charAt(index) == BRACKET_CLOSE) {
                    inRange = false;
                }
                value += formula.charAt(index);
                index++;
                continue;
            }

            // error values
            // end marks a token, determined from absolute list of values

            if (inError) {
                value += formula.charAt(index);
                index++;
                if (indexOf(ERRORS, value) != -1) {
                    inError = false;
                    tokens1.add(new ExcelFormulaToken(value, ExcelFormulaTokenType.Operand,
                                                      ExcelFormulaTokenSubtype.Error));
                    value = "";
                }
                continue;
            }

            // scientific notation check

            if ((OPERATORS_SN).indexOf(formula.charAt(index)) != -1) {
                if (value.length() > 1) {
                    if (value.matches("^[1-9]{1}(\\.[0-9]+)?E{1}$")) {
                        value += formula.charAt(index);
                        index++;
                        continue;
                    }
                }
            }

            // independent character evaluation (order not important)

            // establish state-dependent character evaluations

            if (formula.charAt(index) == QUOTE_DOUBLE) {
                if (value.length() > 0) { // unexpected
                    tokens1.add(new ExcelFormulaToken(value, ExcelFormulaTokenType.Unknown));
                    value = "";
                }
                inString = true;
                index++;
                continue;
            }

            if (formula.charAt(index) == QUOTE_SINGLE) {
                if (value.length() > 0) { // unexpected
                    tokens1.add(new ExcelFormulaToken(value, ExcelFormulaTokenType.Unknown));
                    value = "";
                }
                inPath = true;
                index++;
                continue;
            }

            if (formula.charAt(index) == BRACKET_OPEN) {
                inRange = true;
                value += BRACKET_OPEN;
                index++;
                continue;
            }

            if (formula.charAt(index) == ERROR_START) {
                if (value.length() > 0) { // unexpected
                    tokens1.add(new ExcelFormulaToken(value, ExcelFormulaTokenType.Unknown));
                    value = "";
                }
                inError = true;
                value += ERROR_START;
                index++;
                continue;
            }

            // mark start and end of arrays and array rows

            if (formula.charAt(index) == BRACE_OPEN) {
                if (value.length() > 0) { // unexpected
                    tokens1.add(new ExcelFormulaToken(value, ExcelFormulaTokenType.Unknown));
                    value = "";
                }
                stack.Push(tokens1.add(new ExcelFormulaToken("ARRAY", ExcelFormulaTokenType.Function,
                                                             ExcelFormulaTokenSubtype.Start)));
                stack.Push(tokens1.add(new ExcelFormulaToken("ARRAYROW", ExcelFormulaTokenType.Function,
                                                             ExcelFormulaTokenSubtype.Start)));
                index++;
                continue;
            }

            if (formula.charAt(index) == SEMICOLON) {
                if (value.length() > 0) {
                    tokens1.add(new ExcelFormulaToken(value, ExcelFormulaTokenType.Operand));
                    value = "";
                }
                tokens1.add(stack.pop());
                tokens1.add(new ExcelFormulaToken(",", ExcelFormulaTokenType.Argument));
                stack.Push(tokens1.add(new ExcelFormulaToken("ARRAYROW", ExcelFormulaTokenType.Function,
                                                             ExcelFormulaTokenSubtype.Start)));
                index++;
                continue;
            }

            if (formula.charAt(index) == BRACE_CLOSE) {
                if (value.length() > 0) {
                    tokens1.add(new ExcelFormulaToken(value, ExcelFormulaTokenType.Operand));
                    value = "";
                }
                tokens1.add(stack.pop());
                tokens1.add(stack.pop());
                index++;
                continue;
            }

            // trim white-space

            if (formula.charAt(index) == WHITESPACE) {
                if (value.length() > 0) {
                    tokens1.add(new ExcelFormulaToken(value, ExcelFormulaTokenType.Operand));
                    value = "";
                }
                tokens1.add(new ExcelFormulaToken("", ExcelFormulaTokenType.Whitespace));
                index++;
                while ((formula.charAt(index) == WHITESPACE) && (index < formula.length())) {
                    index++;
                }
                continue;
            }

            // multi-character comparators

            if ((index + 2) <= formula.length()) {
                if (indexOf(COMPARATORS_MULTI, formula.substring(index, index + 2)) != -1) {
                    if (value.length() > 0) {
                        tokens1.add(new ExcelFormulaToken(value, ExcelFormulaTokenType.Operand));
                        value = "";
                    }
                    tokens1.add(new ExcelFormulaToken(formula.substring(index, index + 2),
                                                      ExcelFormulaTokenType.OperatorInfix,
                                                      ExcelFormulaTokenSubtype.Logical));
                    index += 2;
                    continue;
                }
            }

            // standard infix operators

            if ((OPERATORS_INFIX).indexOf(formula.charAt(index)) != -1) {
                if (value.length() > 0) {
                    tokens1.add(new ExcelFormulaToken(value, ExcelFormulaTokenType.Operand));
                    value = "";
                }
                tokens1.add(new ExcelFormulaToken(String.valueOf(formula.charAt(index)),
                                                  ExcelFormulaTokenType.OperatorInfix));
                index++;
                continue;
            }

            // standard postfix operators (only one)

            if ((OPERATORS_POSTFIX).indexOf(formula.charAt(index)) != -1) {
                if (value.length() > 0) {
                    tokens1.add(new ExcelFormulaToken(value, ExcelFormulaTokenType.Operand));
                    value = "";
                }
                tokens1.add(new ExcelFormulaToken(String.valueOf(formula.charAt(index)),
                                                  ExcelFormulaTokenType.OperatorPostfix));
                index++;
                continue;
            }

            // start subexpression or function

            if (formula.charAt(index) == PAREN_OPEN) {
                if (value.length() > 0) {
                    stack.Push(tokens1.add(new ExcelFormulaToken(value, ExcelFormulaTokenType.Function,
                                                                 ExcelFormulaTokenSubtype.Start)));
                    value = "";
                } else {
                    stack.Push(tokens1.add(new ExcelFormulaToken("", ExcelFormulaTokenType.Subexpression,
                                                                 ExcelFormulaTokenSubtype.Start)));
                }
                index++;
                continue;
            }

            // function, subexpression, or array parameters, or operand unions

            if (formula.charAt(index) == COMMA) {
                if (value.length() > 0) {
                    tokens1.add(new ExcelFormulaToken(value, ExcelFormulaTokenType.Operand));
                    value = "";
                }
                if (stack.current().getType() != ExcelFormulaTokenType.Function) {
                    tokens1.add(new ExcelFormulaToken(",", ExcelFormulaTokenType.OperatorInfix,
                                                      ExcelFormulaTokenSubtype.Union));
                } else {
                    tokens1.add(new ExcelFormulaToken(",", ExcelFormulaTokenType.Argument));
                }
                index++;
                continue;
            }

            // stop subexpression

            if (formula.charAt(index) == PAREN_CLOSE) {
                if (value.length() > 0) {
                    tokens1.add(new ExcelFormulaToken(value, ExcelFormulaTokenType.Operand));
                    value = "";
                }
                tokens1.add(stack.pop());
                index++;
                continue;
            }

            // token accumulation

            value += formula.charAt(index);
            index++;

        }

        // dump remaining accumulation

        if (value.length() > 0) {
            tokens1.add(new ExcelFormulaToken(value, ExcelFormulaTokenType.Operand));
        }

        // move tokenList to new set, excluding unnecessary white-space tokens
        // and converting necessary ones to intersections

        ExcelFormulaTokens tokens2 = new ExcelFormulaTokens(tokens1.count());

        while (tokens1.moveNext()) {

            ExcelFormulaToken token = tokens1.current();

            if (token == null) {
                continue;
            }

            if (token.getType() != ExcelFormulaTokenType.Whitespace) {
                tokens2.add(token);
                continue;
            }

            if ((tokens1.BOF()) || (tokens1.EOF())) {
                continue;
            }

            ExcelFormulaToken previous = tokens1.previous();

            if (previous == null) {
                continue;
            }

            if (!(((previous.getType() == ExcelFormulaTokenType.Function)
                    && (previous.getSubtype() == ExcelFormulaTokenSubtype.Stop))
                    || ((previous.getType() == ExcelFormulaTokenType.Subexpression)
                            && (previous.getSubtype() == ExcelFormulaTokenSubtype.Stop))
                    || (previous.getType() == ExcelFormulaTokenType.Operand))) {
                continue;
            }

            ExcelFormulaToken next = tokens1.next();

            if (next == null) {
                continue;
            }

            if (!(((next.getType() == ExcelFormulaTokenType.Function)
                    && (next.getSubtype() == ExcelFormulaTokenSubtype.Start))
                    || ((next.getType() == ExcelFormulaTokenType.Subexpression)
                            && (next.getSubtype() == ExcelFormulaTokenSubtype.Start))
                    || (next.getType() == ExcelFormulaTokenType.Operand))) {
                continue;
            }

            tokens2.add(new ExcelFormulaToken("", ExcelFormulaTokenType.OperatorInfix,
                                              ExcelFormulaTokenSubtype.Intersection));

        }

        // move tokens to final list, switching infix "-" operators to prefix
        // when appropriate, switching infix "+" operators
        // to noop when appropriate, identifying operand and infix-operator
        // subtypes, and pulling "@" from function names

        tokens = new ArrayList<ExcelFormulaToken>(tokens2.count());

        while (tokens2.moveNext()) {

            ExcelFormulaToken token = tokens2.current();

            if (token == null) {
                continue;
            }

            ExcelFormulaToken previous = tokens2.previous();
            ExcelFormulaToken next = tokens2.next();

            if ((token.getType() == ExcelFormulaTokenType.OperatorInfix) && (token.getValue() == "-")) {
                if (tokens2.BOF()) {
                    token.setType(ExcelFormulaTokenType.OperatorPrefix);
                } else if (((previous.getType() == ExcelFormulaTokenType.Function)
                        && (previous.getSubtype() == ExcelFormulaTokenSubtype.Stop))
                        || ((previous.getType() == ExcelFormulaTokenType.Subexpression)
                                && (previous.getSubtype() == ExcelFormulaTokenSubtype.Stop))
                        || (previous.getType() == ExcelFormulaTokenType.OperatorPostfix)
                        || (previous.getType() == ExcelFormulaTokenType.Operand)) {
                    token.setSubtype(ExcelFormulaTokenSubtype.Math);
                } else {
                    token.setType(ExcelFormulaTokenType.OperatorPrefix);
                }

                tokens.add(token);
                continue;
            }

            if ((token.getType() == ExcelFormulaTokenType.OperatorInfix) && (token.getValue() == "+")) {
                if (tokens2.BOF()) {
                    continue;
                } else if (((previous.getType() == ExcelFormulaTokenType.Function)
                        && (previous.getSubtype() == ExcelFormulaTokenSubtype.Stop))
                        || ((previous.getType() == ExcelFormulaTokenType.Subexpression)
                                && (previous.getSubtype() == ExcelFormulaTokenSubtype.Stop))
                        || (previous.getType() == ExcelFormulaTokenType.OperatorPostfix)
                        || (previous.getType() == ExcelFormulaTokenType.Operand)) {
                    token.setSubtype(ExcelFormulaTokenSubtype.Math);
                } else {
                    continue;
                }

                tokens.add(token);
                continue;
            }

            if ((token.getType() == ExcelFormulaTokenType.OperatorInfix)
                    && (token.getSubtype() == ExcelFormulaTokenSubtype.Nothing)) {
                if (("<>=").indexOf(token.getValue().substring(0, 1)) != -1) {
                    token.setSubtype(ExcelFormulaTokenSubtype.Logical);
                } else if (token.getValue() == "&") {
                    token.setSubtype(ExcelFormulaTokenSubtype.Concatenation);
                } else {
                    token.setSubtype(ExcelFormulaTokenSubtype.Math);
                }

                tokens.add(token);
                continue;
            }

            if ((token.getType() == ExcelFormulaTokenType.Operand)
                    && (token.getSubtype() == ExcelFormulaTokenSubtype.Nothing)) {
                if ((token.getValue() == "TRUE") || (token.getValue() == "FALSE")) {
                    token.setSubtype(ExcelFormulaTokenSubtype.Logical);
                } else {
                    try {
                        double d = Double.parseDouble(token.getValue());
                        token.setSubtype(ExcelFormulaTokenSubtype.Number);
                    } catch (Exception e) {
                        token.setSubtype(ExcelFormulaTokenSubtype.Range);
                    }
                }

                tokens.add(token);
                continue;
            }

            if (token.getType() == ExcelFormulaTokenType.Function) {
                if (token.getValue().length() > 0) {
                    if (token.getValue().substring(0, 1) == "@") {
                        token.setValue(token.getValue().substring(1));
                    }
                }
            }

            tokens.add(token);

        }

    }

    private int indexOf(Object[] array, Object o) {
        if (null == o) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < array.length; i++) {
                if (o.equals(array[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

}
