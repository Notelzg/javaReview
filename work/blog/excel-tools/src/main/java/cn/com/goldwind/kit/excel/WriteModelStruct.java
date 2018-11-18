package cn.com.goldwind.kit.excel;

import cn.com.goldwind.kit.excel.aviator.function.date.DateFormat;
import cn.com.goldwind.kit.excel.model.CellModel;

import java.util.*;

public class WriteModelStruct {

    /**
     * 读取Excel文件的所有数据，除了不能处理的table  fixed 函数
     * @author zgli
     * @param path   Excel 文件路径
     * @return  数据集合
     * @throws Exception
     */
    public  Map<String, CellModel> getCell(String path) throws Exception {
        ExcelExtract ee = new ExcelExtract(path);
        int idxs = ee.get_workbook().getNumberOfSheets();
        Date start = new Date();
        System.out.println("started. " + DateFormat.getFullDatetimeFormatter().format(start));
        Map<String, CellModel> m = ee.formulaExtract(idxs, 2);
        Date end = new Date();
        System.out.println("extract finished. " + DateFormat.getFullDatetimeFormatter().format(end));
        ee.calculateBatchLevel(m);
        start = new Date();
       System.out.println("calculate finished. " + DateFormat.getFullDatetimeFormatter().format(start));
        return  m;
    }


}
