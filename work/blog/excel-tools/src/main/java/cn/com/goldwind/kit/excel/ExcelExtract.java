/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel;

import cn.com.goldwind.kit.excel.aviator.function.date.DateFormat;
import cn.com.goldwind.kit.excel.model.CellModel;
import cn.com.goldwind.kit.excel.parser.ExcelFormulaParser;
import cn.com.goldwind.kit.excel.parser.ExcelFormulaToken;
import cn.com.goldwind.kit.excel.parser.ExcelFormulaTokenSubtype;
import cn.com.goldwind.kit.excel.parser.ExcelFormulaTokenType;
import cn.com.goldwind.kit.excel.processor.ITokenProcessor;
import cn.com.goldwind.kit.excel.processor.ProcessorFactory;
import cn.com.goldwind.springboot.framework.kit.StrKit;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lfjin
 *
 */
public class ExcelExtract {

    private File                 _xlsxFile;
    private Workbook             _workbook;
    private boolean              _date1904;
    private FormulaEvaluator     _evaluator;

    private static Pattern       VALUE_FUNCTION_PATTERN = Pattern.compile("VALUE\\(", Pattern.CASE_INSENSITIVE);
    private static Pattern       FIXED_FUNCTION_PATTERN = Pattern.compile("FIXED\\(", Pattern.CASE_INSENSITIVE);

    private static Pattern       CELL_REFERENCE_PATTERN = Pattern
            .compile("([A-Za-z0-9\\u4e00-\\u9fa5]+_[A-Z]+[1-9][0-9]*)");

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

    public ExcelExtract(String filePath) throws IOException {
        _xlsxFile = new File(filePath);
        // 初始化输入流
        FileInputStream is = new FileInputStream(_xlsxFile);
        // 创建Excel,并指定Excel读取位置
        _workbook = new XSSFWorkbook(is);
        _evaluator = _workbook.getCreationHelper().createFormulaEvaluator();
        _date1904 = ((XSSFWorkbook) _workbook).isDate1904();
    }

    public ExcelExtract(File xlsxFile) throws IOException {
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
                        Date date = cell.getDateCellValue();
                        value = String.valueOf(DateFormat.getFullDatetimeFormatter().format(date));
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

                    String tanslate = translateFormula(cell, cm.getFormula());
                    cm.setAviatorFormula(tanslate);
                    try {
                        /* 如果是公式的话，取一下公式的计算值, 为了对比模型计算值和Excel计算值是否一致 */
                        cm.setFormula(String.valueOf(cell.getNumericCellValue()));
                    } catch (Exception e) {
                        // continue;
                    }
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

        // StringBuffer sb = new StringBuffer();
        ExcelFormulaParser parser = new ExcelFormulaParser(formula.replaceAll("\\'", ""));
        List<ExcelFormulaToken> l = parser.getTokens();
        Stack<String> functions = new Stack<>();
        Stack<Integer> funcArgNums = new Stack<>();
        List<String> rtnString = new ArrayList<String>();
        for (int i = 0; i < l.size(); i++) {
            ExcelFormulaToken token = l.get(i);

            if (cell != null) {
                token.setSheetName(cell.getSheet().getSheetName());
            }
            if (!functions.empty()) {
                token.setFunctionName(functions.peek() + funcArgNums.peek());
            }
            if (token.getType() == ExcelFormulaTokenType.Function) {
                if (token.getSubtype() == ExcelFormulaTokenSubtype.Start) {
                    functions.push(token.getValue());
                    funcArgNums.push(0);
                } else if (token.getSubtype() == ExcelFormulaTokenSubtype.Stop) {
                    functions.pop();
                    funcArgNums.pop();
                }
            } else if (token.getType() == ExcelFormulaTokenType.Argument) {
                Integer argNums = funcArgNums.pop();
                funcArgNums.push(argNums + 1);
            } else if (token.getType() == ExcelFormulaTokenType.OperatorPostfix) {
                String percentNumber = rtnString.get(i - 1);
                Double number = Double.valueOf(percentNumber);
                rtnString.set(i - 1, String.valueOf(number / 100.0));
            }
            ITokenProcessor processor = ProcessorFactory.getProcessor(token);
            rtnString.add(processor.process(token));
        }
        return StrKit.join(rtnString, "");
    }

    public Map<String, CellModel> calculateBatchLevel(Map<String, CellModel> map) {
        Iterator<Map.Entry<String, CellModel>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, CellModel> entry = it.next();
            CellModel model = entry.getValue();
            if (model.getLevel() == null) {
                int level = recursion(entry.getKey(), map);
                model.setLevel(level);
                map.put(entry.getKey(), model);
            }
        }

        return map;
    }

    private int recursion(String key, Map<String, CellModel> map) {
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
        while (m.find()) {
            String tmp = m.group();
            if (!paramList.contains(tmp)) {
                paramList.add(tmp);
            }
        }

        Collections.sort(paramList);
        Collections.reverse(paramList);

        for (String param : paramList) {
            // System.out.println(param);

            CellModel cm = map.get(param);
            if (cm == null) {
                continue;
            }
            if (cm.getCellType().equals("2")) {
                cm.setLevel(0);
                map.put(param, cm);
                continue;
            }
            if (cm.getLevel() == null) {
                int result = recursion(param, map);
                cm.setLevel(result);
                map.put(param, cm);
            }

        }

        int tmpLevel = 0;
        for (String param : paramList) {
            CellModel cm = map.get(param);
            if (cm != null) {
                tmpLevel = Math.max(tmpLevel, cm.getLevel());
            }
        }

        return tmpLevel + 1;
    }

    public Workbook get_workbook() {
        return _workbook;
    }

    public void set_workbook(Workbook _workbook) {
        this._workbook = _workbook;
    }

    public static void main(String[] args) throws Exception {

        ExcelExtract ee = new ExcelExtract(args[0]);
        int idxs = ee._workbook.getNumberOfSheets();
        Date start = new Date();
        System.out.println("started. " + DateFormat.getFullDatetimeFormatter().format(start));
        Map<String, CellModel> m = ee.formulaExtract(idxs, 2);
        Date end = new Date();
        System.out.println("extract finished. " + DateFormat.getFullDatetimeFormatter().format(end));
        ee.calculateBatchLevel(m);
        start = new Date();
        System.out.println("calculate finished. " + DateFormat.getFullDatetimeFormatter().format(start));

        BufferedWriter bw = new BufferedWriter(new FileWriter(ee.getFile().getAbsolutePath()
                .replaceAll(".xls", "_trade_new.txt")));
        List<String> rels = new ArrayList<String>();
        for (String key : m.keySet()) {
            // bw.write(key + "=" + m.get(key).getAviatorFormula() + ",level=" +
            // m.get(key).getLevel());
            bw.write(m.get(key).toString());
            bw.newLine();
        }
        bw.close();
    }

}
