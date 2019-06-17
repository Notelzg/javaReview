package chap18_IOStream;

import java.io.*;

public class BinaryFile {
    public static byte[] read(File file) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        try {
            byte[] rs = new byte[bis.available()];
            bis.read(rs);
            return  rs;
        }finally {
            bis.close();
        }
    }
    public static byte[] read(String filePath) throws IOException {
        return read(new File(filePath));
    }

}
