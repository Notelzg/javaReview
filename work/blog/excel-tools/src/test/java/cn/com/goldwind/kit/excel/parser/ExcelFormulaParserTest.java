/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.parser;

import cn.com.goldwind.kit.excel.aviator.function.date.DateFormat;
import in.satpathy.financial.XIRRData;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lfjin
 *
 */
public class ExcelFormulaParserTest {
    @Test
    public void testParser() {
        String f = "IF(AD1-附表8电价限电预测表_N15<=(附表3.基本假设信息表_B78+附表3.基本假设信息表!B79)*4,-HLOOKUP(AD3,附表3.基本假设信息表!B85:BU90,3,FALSE),0)";
        ExcelFormulaParser parser = new ExcelFormulaParser(f);
        List l = parser.getTokens();
        for (Object object : l) {
            // System.out.println(object.toString());
        }
        Pattern p = Pattern.compile("(\\W+_[A-Z]+[1-9][0-9]*)");
        Matcher m = p.matcher(f);
        System.out.println(m.find());

        AreaReference area = new AreaReference("附表3.基本假设信息表!$B$85:$BU$90", SpreadsheetVersion.EXCEL2007);
        area = new AreaReference("附表7.辅助计算表!$P$55:BJ56", SpreadsheetVersion.EXCEL2007);
        area = new AreaReference("附表7.辅助计算表!$P$55", SpreadsheetVersion.EXCEL2007);
        CellReference[] refs = area.getAllReferencedCells();
        for (CellReference cellReference : refs) {
            String[] sss = cellReference.getCellRefParts();
            System.out.println();
        }

        Calendar c1 = new GregorianCalendar(1900, 0, 1);
        c1.add(Calendar.DATE, 40055);
        System.out.println(c1.getTime());
        int days = XIRRData.getDaysBetween(c1, Calendar.getInstance());

        try {
            System.out.println(DateFormat.getDateFormatter().parse("2018-05-06"));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(days);
    }

    @Test
    public void testGetValue() throws Exception {
//        File file = new File("c://附件1.陆上集中风电模型(3).xlsm");

        // 初始化输入流
//        FileInputStream is = new FileInputStream(file);
        // 创建Excel,并指定Excel读取位置
//        Workbook wb = new XSSFWorkbook(is);
//        // wb.getCreationHelper().createFormulaEvaluator().evaluateAll();
//        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
//        evaluator.evaluateAll();
//        Sheet sheet6 = wb.getSheet("附表6.交易测算及敏感性分析");
//        Row row = sheet6.getRow(30);
//        Cell cell = row.getCell(5);
//
//        System.out.println(cell);

    }
}
