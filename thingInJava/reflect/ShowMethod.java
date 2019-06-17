package reflect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ShowMethod {
    private static Pattern pattern = Pattern.compile("\\w+\\.");
    public static  void main(String[] args){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please input class name: ");
            while (scanner.hasNext())
            {
                String className = scanner.next();
                System.out.print("Please input method  regex: ");
                String regex = scanner.next();
                System.out.format("class name is %s, regex is %s", className, regex);
                System.out.println();
                Class c = Class.forName(className);
                for (Method method : c.getDeclaredMethods()) {
                    if (method.getName().matches(regex))
                        System.out.println(pattern.matcher(method.toString()).replaceAll(""));
                }
                System.out.print("Please input class name: ");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
