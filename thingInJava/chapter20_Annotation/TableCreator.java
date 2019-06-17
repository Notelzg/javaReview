package chapter20_Annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableCreator {
    public static void main(String[] args) throws ClassNotFoundException {
        String className = "chapter20_Annotation.Member";
        Class<?>  cl = Class.forName(className);
        SqlAnnotation.DBTable dbTable = cl.getAnnotation(SqlAnnotation.DBTable.class);
        String tableName = dbTable.name();
        if (dbTable.name().length() < 1){
           tableName = cl.getName();
        }
        List<String> columnDefs = new ArrayList<>() ;
        for (Field field : cl.getDeclaredFields()){
            String columnName = null;
            Annotation[] annotations = field.getAnnotations();
            if(annotations.length < 1)
                continue;
            if (annotations[0] instanceof SqlAnnotation.SqlString){
                SqlAnnotation.SqlString sqlString = (SqlAnnotation.SqlString)annotations[0];
                if (sqlString.name().length() < 1)
                    columnName = field.getName().toUpperCase();
                columnDefs.add(columnName + "  Varchar（" + sqlString.value() + ")"
                + getConstranits(sqlString.constraints()));
            }
            if (annotations[0] instanceof SqlAnnotation.SqlInteger){
                SqlAnnotation.SqlInteger sqlInteger = (SqlAnnotation.SqlInteger)annotations[0];
                if (sqlInteger.name().length() < 1)
                    columnName = field.getName().toUpperCase();
                columnDefs.add(columnName + " Integer(" + sqlInteger.value() + ")"
                        + getConstranits(sqlInteger.constraints()));
            }

        StringBuilder createCommand = new StringBuilder(
                "Create table " + tableName + "(");
        for (String column : columnDefs){
            createCommand.append("\n" + column + ",");
        }
       String tableCreate = createCommand.substring(0, createCommand.length() - 1)  + ")";
        System.out.println(tableCreate);

        }
    }
    public  static  String getConstranits(SqlAnnotation.Constraints constraints){
        String rs = "";
        if (!constraints.allowNull())
            rs += "not null";
        if (constraints.primaryKey())
            rs += " primary key";
        if (constraints.unique())
            rs += "unique";

        return  rs;
    }
}
