/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.com.goldwind.kit.excel.aviator.function.date.DateFormat;
import cn.com.goldwind.kit.excel.model.CellModel;
import cn.com.goldwind.kit.excel.parser.ExcelFormulaParser;
import cn.com.goldwind.kit.excel.parser.ExcelFormulaToken;
import cn.com.goldwind.kit.excel.parser.ExcelFormulaTokenSubtype;
import cn.com.goldwind.kit.excel.parser.ExcelFormulaTokenType;
import cn.com.goldwind.kit.excel.processor.ITokenProcessor;
import cn.com.goldwind.kit.excel.processor.ProcessorFactory;
import cn.com.goldwind.springboot.framework.kit.StrKit;

/**
 * @author lfjin
 *
 */
public class ExcelCheck {

    private File                 _xlsxFile;
    private Workbook             _workbook;
    private boolean              _date1904;
    private FormulaEvaluator     _evaluator;

    private static Pattern       VALUE_FUNCTION_PATTERN = Pattern.compile("VALUE\\(", Pattern.CASE_INSENSITIVE);
    private static Pattern       FIXED_FUNCTION_PATTERN = Pattern.compile("FIXED\\(", Pattern.CASE_INSENSITIVE);

    private static Pattern       CELL_REFERENCE_PATTERN = Pattern
            .compile("([A-Z0-9\\u4e00-\\u9fa5]+_[A-Z]+[1-9][0-9]*)");

    private static List<Pattern> EXCLUDE_FUNCTIONS;
    static {
        EXCLUDE_FUNCTIONS = new ArrayList<>();
        EXCLUDE_FUNCTIONS.add(VALUE_FUNCTION_PATTERN);
        EXCLUDE_FUNCTIONS.add(FIXED_FUNCTION_PATTERN);
    }

    /**
     * @return the _xlsxFile
     */
    public File getFile() {
        return _xlsxFile;
    }

    public ExcelCheck(String filePath) throws IOException {
        _xlsxFile = new File(filePath);
        // 初始化输入流
        FileInputStream is = new FileInputStream(_xlsxFile);
        // 创建Excel,并指定Excel读取位置
        _workbook = new XSSFWorkbook(is);
        _evaluator = _workbook.getCreationHelper().createFormulaEvaluator();
        _date1904 = ((XSSFWorkbook) _workbook).isDate1904();
    }

    public ExcelCheck(File xlsxFile) throws IOException {
        this._xlsxFile = xlsxFile;
        // 初始化输入流
        FileInputStream is = new FileInputStream(_xlsxFile);
        // 创建Excel,并指定Excel读取位置
        _workbook = new XSSFWorkbook(is);
        _evaluator = _workbook.getCreationHelper().createFormulaEvaluator();
        _date1904 = ((XSSFWorkbook) _workbook).isDate1904();
    }

    public Map<String, CellModel> formulaExtract(int[] sheetIdxs) throws Exception {
        Map<String, CellModel> rtn = new HashMap<>();
        for (int i : sheetIdxs) {
            Map<String, CellModel> tmp = formulaExtractPerSheet(i);
            rtn.putAll(tmp);
        }

        return rtn;
    }

    public Map<String, CellModel> formulaExtract(int sheetIdxs) throws Exception {
        return formulaExtract(sheetIdxs, 0);
    }

    public Map<String, CellModel> formulaExtract(int sheetIdxs, int skeep) throws Exception {
        Map<String, CellModel> rtn = new HashMap<>();
        for (int i = skeep; i < sheetIdxs; i++) {
            Map<String, CellModel> tmp = formulaExtractPerSheet(i);
            rtn.putAll(tmp);
        }

        return rtn;
    }

    public Map<String, CellModel> formulaExtractPerSheet(int sheetIdx) throws Exception {
        Map<String, CellModel> rtn = new HashMap<>();
        // 根据Workbook得到第0个下标的工作薄
        Sheet sheet = _workbook.getSheetAt(sheetIdx);
        System.out.println("Sheet " + sheet.getSheetName());
        // 遍历工作薄中的所有行,注意该foreach只有Java5或者以上才支持
        for (Row row : sheet) {
            // 遍历行中的所有单元格 Java5+ 才能使用
            for (Cell cell : row) {
                // 单元格的参照 ,根据行和列确定某一个单元格的位置
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                // 得到单元格类型
                String value = null;

                CellModel cm = new CellModel();
                cm.setSheetName(sheet.getSheetName());
                cm.setReference(cellRef.formatAsString());
                String key = cm.getFullReference();

                switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:// 数字类型
                    cm.setCellType("2");// 常量
                    // 检查单元格是否包含一个Date类型
                    // 仅仅只检查Excel内部的日期格式,
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 输出日期
                        value = String.valueOf(cell.getDateCellValue());
                    } else {
                        // 输出数字
                        value = String.valueOf(cell.getNumericCellValue());
                    }
                    cm.setFormula(value);
                    rtn.put(key, cm);
                    break;
                case Cell.CELL_TYPE_STRING:// 字符类型
                    cm.setCellType("2");// 常量
                    value = cell.getStringCellValue();
                    cm.setFormula(value);
                    rtn.put(key, cm);
                    break;
                case Cell.CELL_TYPE_FORMULA:// 公式
                    cm.setCellType("1");// 公式
                    cm.setFormula(cell.getCellFormula());
                    // System.out.println(key + "=" + cm.getFormula());
                    String tanslate = translateFormula(cell, cm.getFormula());
                    cm.setAviatorFormula(tanslate);

                    if (tanslate != null) {
                        // String key = cm.getFullReference();
                        rtn.put(key, cm);
                    }
                    break;
                default:
                }
            }
        }
        return rtn;
    }

    public String translateFormula(Cell cell, String formula) {
        // formula =
        // "SUM(OFFSET(B90,0,0,1,附表8.电价限电预测表!$N$15))+SUM(OFFSET(B96,0,0,1,附表8.电价限电预测表!$N$15))+SUM(OFFSET(B102,0,0,1,附表8.电价限电预测表!$N$15))";

        for (Pattern p : EXCLUDE_FUNCTIONS) {
            Matcher matcher = p.matcher(formula);
            if (matcher.find()) {
                return null;
            }
        }

        StringBuffer sb = new StringBuffer();
        ExcelFormulaParser parser = new ExcelFormulaParser(formula.replaceAll("\\'", ""));
        List<ExcelFormulaToken> l = parser.getTokens();
        Stack<String> functions = new Stack<>();
        for (ExcelFormulaToken token : l) {
            token.setSheetName(cell.getSheet().getSheetName());
            if (!functions.empty()) {
                token.setFunctionName(functions.peek());
            }
            if (token.getType() == ExcelFormulaTokenType.Function) {
                if (token.getSubtype() == ExcelFormulaTokenSubtype.Start) {
                    functions.push(token.getValue());
                } else if (token.getSubtype() == ExcelFormulaTokenSubtype.Stop) {
                    functions.pop();
                }
            }
            ITokenProcessor processor = ProcessorFactory.getProcessor(token);
            sb.append(processor.process(token));
        }
        return sb.toString();
    }

    public Map<String, CellModel> calculateBatchLevel(Map<String, CellModel> map) throws Exception {
        Iterator<Map.Entry<String, CellModel>> it = map.entrySet().iterator();
        BufferedWriter bw = new BufferedWriter(new FileWriter(getFile().getAbsolutePath()
                .replaceAll(".xlsm", "_trade_error.txt").replaceAll(".xlsx", "_trade_error.txt")));
        while (it.hasNext()) {
            Map.Entry<String, CellModel> entry = it.next();
            CellModel model = entry.getValue();
            if (model.getLevel() == null) {
                int level = recursion(entry.getKey(), map, bw);
                model.setLevel(level);
                map.put(entry.getKey(), model);
            }
        }

        bw.close();

        return map;
    }

    private int recursion(String key, Map<String, CellModel> map, BufferedWriter bw) throws Exception {
        CellModel model = map.get(key);
        // if (model == null) {
        // System.out.print("trading : " + key);
        // System.out.println(" = " + input);
        // }
        String input = model.getAviatorFormula();

        if (StrKit.isBlank(input)) {
            return 0;
        }
        Matcher m = CELL_REFERENCE_PATTERN.matcher(input);
        List<String> paramList = new ArrayList<String>();
        // System.out.println(key + "=" + input);
        while (m.find()) {
            String tmp = m.group();
            if (!paramList.contains(tmp)) {
                paramList.add(tmp);
            }
        }

        Collections.sort(paramList);
        Collections.reverse(paramList);

        List<String> errorParams = new ArrayList<>();
        for (String param : paramList) {

            if (!map.containsKey(param)) {
                errorParams.add(param);
                continue;
            }

            CellModel cm = map.get(param);
            if (cm.getLevel() == null) {
                int result = recursion(param, map, bw);
                cm.setLevel(result);
                map.put(key, cm);
            }
        }

        if (!errorParams.isEmpty()) {
            bw.write("Trading " + model.getFullReference() + "=" + model.getFormula() + ". Founded unknow params "
                    + StrKit.join(errorParams, ", "));
            bw.newLine();
        }

        int tmpLevel = 0;

        return tmpLevel + 1;
    }

    public static void main(String[] args) throws Exception {
        ExcelCheck ee = new ExcelCheck(args[0]);
        int idxs = ee._workbook.getNumberOfSheets();

        Date start = new Date();
        System.out.println("started. " + DateFormat.getFullDatetimeFormatter().format(start));
        Map<String, CellModel> m = ee.formulaExtract(idxs, 2);
        Date end = new Date();
        System.out.println("extract finished. " + DateFormat.getFullDatetimeFormatter().format(end));
        ee.calculateBatchLevel(m);
        start = new Date();
        System.out.println("calculate finished. " + DateFormat.getFullDatetimeFormatter().format(start));

    }

}
