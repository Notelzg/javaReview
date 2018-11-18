/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.processor.impl;

import cn.com.goldwind.kit.excel.model.CellModel;
import cn.com.goldwind.kit.excel.parser.ExcelFormulaToken;
import cn.com.goldwind.kit.excel.parser.ExcelFormulaTokenSubtype;
import cn.com.goldwind.kit.excel.processor.ITokenProcessor;
import cn.com.goldwind.springboot.framework.kit.StrKit;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lfjin
 *
 */
public class ArgumentProcessor implements ITokenProcessor {
    /*
     * (non-Javadoc)
     *
     * @see cn.com.goldwind.kit.excel.processor.ITokenProcess#process()
     */
    @Override
    public String process(ExcelFormulaToken token) {
        String value = token.getValue();
        if (ExcelFormulaTokenSubtype.Logical == token.getSubtype()) {
            return value.toLowerCase();
        } else if (ExcelFormulaTokenSubtype.Range == token.getSubtype()) {
            return extractRange(token);
        } else if (ExcelFormulaTokenSubtype.Text == token.getSubtype()) {
            if ("FALSE".equalsIgnoreCase(value) || "TRUE".equalsIgnoreCase(value)) {
                return value;
            }
            return "'" + value + "'";
        } else if (ExcelFormulaTokenSubtype.Number == token.getSubtype()) {
            return Double.valueOf(token.getValue()).toString();
        } else {
            return value;
        }
    }

    private String extractRange(ExcelFormulaToken token) {

        String value = token.getValue().replaceAll("\\$", "");
        if ("FALSE".equalsIgnoreCase(value) || "TRUE".equalsIgnoreCase(value)) {
            return value;
        }

        String[] ranges = value.split(":");

        String referenceString = value;
        if (ranges.length == 2) {
            String second = ranges[1];
            int pos = second.indexOf("!");
            if (pos != -1) {
                referenceString = ranges[0] + ":" + second.substring(pos + 1);
            }
        }
        AreaReference area = new AreaReference(referenceString, SpreadsheetVersion.EXCEL2007);

        CellReference[] refs = area.getAllReferencedCells();
        List<String> cellList = new ArrayList<>();
        for (CellReference cellReference : refs) {
            CellModel cm = new CellModel(cellReference);
            if (cellReference.getSheetName() == null) {
                cm.setSheetName(token.getSheetName());
            }
            cellList.add(cm.getFullReference());
        }

        String rtn = StrKit.join(cellList, ",");

        if ("HLOOKUP1".equalsIgnoreCase(token.getFunctionName()) || "VLOOKUP1".equalsIgnoreCase(token.getFunctionName())
                || "INDEX0".equalsIgnoreCase(token.getFunctionName())
                || "SUMIFS0".equalsIgnoreCase(token.getFunctionName())) {
            CellReference firstCell = area.getFirstCell();
            CellReference lastCell = area.getLastCell();
            rtn = String.valueOf(lastCell.getCol() - firstCell.getCol() + 1) + "," + rtn;
        }

        return rtn;
    }

}
