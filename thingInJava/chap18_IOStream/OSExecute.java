package chap18_IOStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OSExecute {
    public static void print(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String s;
        while ((s = br.readLine()) != null)
            System.out.println(s);
    }

    public static void command(String command){
        boolean err = false;
        try{
           Process process = new ProcessBuilder(command.split(" ")).start();
           print(process.getInputStream());
           print(process.getErrorStream());

        }catch (Exception e){

        }
    }
}
