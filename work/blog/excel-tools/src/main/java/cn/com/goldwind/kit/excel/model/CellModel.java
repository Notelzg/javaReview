/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.model;

import java.io.Serializable;

import org.apache.poi.ss.util.CellReference;

/**
 * @author lfjin
 *
 */
public class CellModel implements Serializable {

    private static final long serialVersionUID = -9098964660561912586L;
    private String            sheetName;
    private String            reference;
    private String            formula;
    private String            aviatorFormula;
    private String            cellType;
    private Integer           level;

    public CellModel() {
    }

    public CellModel(CellReference cellReference) {
        String[] parts = cellReference.getCellRefParts();
        this.sheetName = cellReference.getSheetName();
        this.reference = parts[2] + parts[1];
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

    /**
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * @param reference
     *            the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

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
     * @return the aviatorFormula
     */
    public String getAviatorFormula() {
        return aviatorFormula;
    }

    /**
     * @param aviatorFormula
     *            the aviatorFormula to set
     */
    public void setAviatorFormula(String aviatorFormula) {
        this.aviatorFormula = aviatorFormula;
    }

    public String getFullReference() {
        String sheetName = this.sheetName == null ? "" : this.sheetName.replaceAll("\\.", "") + "_";
        return sheetName + this.getReference();
    }

    /**
     * @return the cellType
     */
    public String getCellType() {
        return cellType;
    }

    /**
     * @param cellType
     *            the cellType to set
     */
    public void setCellType(String cellType) {
        this.cellType = cellType;
    }

    /**
     * @return the level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level
     *            the level to set
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return this.getFullReference() + "=" + this.getFormula() + "," + this.getAviatorFormula() + ",level="
                + this.getLevel();
    }

}
