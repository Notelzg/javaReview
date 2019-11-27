package cn.com.goldwind.kit.excel;

import cn.com.goldwind.kit.excel.parser.ExcelFormulaToken;
import cn.com.goldwind.kit.excel.parser.ExcelFormulaTokenSubtype;
import cn.com.goldwind.kit.excel.parser.ExcelFormulaTokenType;
import cn.com.goldwind.kit.excel.processor.impl.ArgumentProcessor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateTdPosition {
    public void generete() throws IOException {
        BufferedWriter bwnull = new BufferedWriter(new FileWriter("c://tdPosition.txt"));
        String argument = "E2:AJ25";
        String sheet = "附表5";
        int rowLength = 32;
        ExcelFormulaToken token = new ExcelFormulaToken(argument, ExcelFormulaTokenType.Argument, ExcelFormulaTokenSubtype.Range );
        token.setSheetName(sheet);
        ArgumentProcessor te = new ArgumentProcessor();
        String[] cells = te.process(token).split(",");
        for (int i=0;cells!=null && i < cells.length; i++){
            if ((i) % rowLength == 0) {
                System.out.println("<tr>");
                bwnull.write("<tr>");
                bwnull.newLine();
            }
            System.out.println("<td position=" + cells[i] + "></td>");
            bwnull.write("<td position=" + cells[i] + "></td>");
            bwnull.newLine();
            if ((i+1) % rowLength == 0) {
                System.out.println("</tr>");
                bwnull.write("</tr");
                bwnull.newLine();
            }
        }
    }

    public static  void main(String[] args){
       GenerateTdPosition gt = new GenerateTdPosition();
       try {
           gt.generete();
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
    }


}
