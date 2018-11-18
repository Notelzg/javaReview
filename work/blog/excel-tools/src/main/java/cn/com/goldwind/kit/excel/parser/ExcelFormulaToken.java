/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.parser;

/**
 * @author lfjin
 *
 */
public class ExcelFormulaToken {

    private String                   value;
    private ExcelFormulaTokenType    type;
    private ExcelFormulaTokenSubtype subtype;
    private String                   sheetName;
    private String                   functionName;

    public ExcelFormulaToken(String value, ExcelFormulaTokenType type) {
        this(value, type, ExcelFormulaTokenSubtype.Nothing);
    }

    public ExcelFormulaToken(String value, ExcelFormulaTokenType type, ExcelFormulaTokenSubtype subtype) {
        this.value = value;
        this.type = type;
        this.subtype = subtype;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the type
     */
    public ExcelFormulaTokenType getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(ExcelFormulaTokenType type) {
        this.type = type;
    }

    /**
     * @return the subtype
     */
    public ExcelFormulaTokenSubtype getSubtype() {
        return subtype;
    }

    /**
     * @param subtype
     *            the subtype to set
     */
    public void setSubtype(ExcelFormulaTokenSubtype subtype) {
        this.subtype = subtype;
    }

    /**
     * @return the functionName
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * @param functionName
     *            the functionName to set
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    /**
     * @return the sheetName
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * @param sheetName
     *            the sheetName to set
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new StringBuilder().append(type).append("---").append(subtype).append("---").append(value).toString();
    }

}
