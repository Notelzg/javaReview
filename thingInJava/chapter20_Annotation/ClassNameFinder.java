package chapter20_Annotation;

import chap18_IOStream.BinaryFile;
import chap18_IOStream.Directory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ClassNameFinder {
    public static String thisClass(byte[] classBytes){
        Map<Integer, Integer>  offsetTable = new HashMap<>();
        Map<Integer, String>  classNameTable = new HashMap<>();
        try{
            DataInputStream data = new DataInputStream(new ByteArrayInputStream(classBytes));
            int magic = data.readInt();
            int minorVersion = data.readShort();
            int maxVersion = data.readShort();
            int constantPoolCount = data.readShort();
            for (int i = 0; i < constantPoolCount; i++){
                int  tag = data.read();
                int tableSize;
                switch (tag){
                    case 1: //UTF
                        int length = data.readShort();
                        char[] bytes  = new char[length];
                        for (int j = 0; j < length; j++)
                            bytes[j] = (char) data.read();
                        String className = new String(bytes);
                        classNameTable.put(i, className);
                        break;
                    case 5 : //long
                    case 6 : // double    8 bytes
                        data.readLong();
                        i++;
                        break;
                    case 7 : //class
                       int offset = data.readShort();
                       offsetTable.put(i, offset);
                       break;
                    case 8 : //string 2 bytes
                        data.readShort();
                        break;
                    case 3 : //integer
                    case 4 : //float
                    case 9 : //field ref
                    case 10 : //method ref
                    case 11 ://interface method ref
                    case 12 :  //name adn type
                        //  4 bytes
                        data.readInt();
                        break;
                    default:
                        throw new RuntimeException("Bad tag" + tag);
                }
            }
           short access_flags = data.readShort();
           int this_class = data.readShort();
           int super_class = data.readShort();
           return classNameTable.get(offsetTable.get(this_class)).replace('/', '.');
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws IOException {
        for(File file : Directory.walk(".", ".*\\.class$")){
            System.out.println(thisClass(BinaryFile.read(file)));
        }

    }
}
