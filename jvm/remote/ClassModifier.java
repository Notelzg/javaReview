package remote;

public class ClassModifier {
    /* 常量池的偏移地址， 前四个是Caffbabe， 后四个是 版本号，从第8个开始是 常量池 */
    public  static  final  int CONSTANT_POOL_COUNT_INDEX = 8;
    /**
     * constant_utf8_info 的tag是
     */
    private static final  int CONSTANT_Utf8_info = 1;

    /**
     * 常量池中11种常量所占的长度， 除了constant——utf8_info 不是定长的
     * utf8 是 1 项目标识         tag     结构
     * CONSTANT_Utf8_info         1    u2=2bytes  1 + 2 = 3 ,2byte存储字符串长度
     * CONSTANT_Integer_info      3    u4=4bytes  1 + 4 = 5
     * CONSTANT_Float_info        4    u4=4bytes  1 + 4 = 5
     * CONSTANT_Long_info         5    u8=8bytes  1 + 8 = 9
     * CONSTANT_Double_info       6    u8=8bytes  1 + 8 = 9
     * CONSTANT_Class_info        7    u2=2bytes  1 + 2 = 3   u2是index，index指向常量池中的一个utf8的类名
     * CONSTANT_String_info       8    u2=2bytes  1 + 2 = 3
     * CONSTANT_Fieldref_info     9    u2+u2=4bytes  1 + 4 = 5   u2指向calss， u2指向NameAndType
     * CONSTANT_Methodref_info    10   u2+u2=4bytes  1 + 4 = 5   u2指向calss， u2指向NameAndType
     * CONSTANT_Interface-method_info 11    u2+u2=4bytes  1 + 4 = 5       u2指向calss， u2指向NameAndType
     * CONSTANT_NameAndType_info      12    u2+u2=4bytes  1 + 4 = 5       u2指向名称 u2指向类型说明/如果是方法则是参数说明和返回值类型说明
     * CONSTANT_MethodHandle_info     15    u1+u2=3bytes  1 + 3 = 4
     * CONSTANT_MethodType_info       16    u2=2bytes  1 + 2 = 3
     * CONSTANT_InvokeDynamic_info    18    u2+u2=4bytes  1 + 4 = 5
     */
    //                                                  0,  1   2  3  4  5  6  7  8  9  10 11 12 15  16 18
    private static final int[] CONSTANT_ITEM_LENTGH = {-1, -1, -1, 5, 5, 9, 9, 3, 3, 5, 5, 5, 5, 4, 3, 5};

    private static final int u1 = 1;
    private static final int u2 = 2;

    private byte[] classByte;

    public ClassModifier(byte[] classByte){
        this.classByte = classByte;
    }

    /**
     * 修改常量池中的常量内容， CONSTANT_Utf8_info
     * @param oldStr 修改前的字符串
     * @param newStr 修改后的字符串
     * 根据字节码文件的格式，找到要替换的字符串，然后进行替换，主要问题在于字节码文件中的内容都是字节
     * 是不是能直接进行替换的，需要进行转换之后再进行对比，
     */
    public byte[] modifyUTF8Constant(String oldStr, String newStr){
        int cpc = getConstantPoolCont();
        // 偏移量在每次取出一个数据之后，需要更新，这里是取出 常量池中常量的个数
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;
        for (int i = 0; i < cpc; i++){
           int tag = BytesUtils.bytes2Int(classByte, offset, u1) ;
           if (tag == CONSTANT_Utf8_info){
               int len =  BytesUtils.bytes2Int(classByte, offset + u1, u2);
               offset+= (u1 + u2);
               String sourceStr = BytesUtils.bytes2String(classByte, offset, len);
               // 相等则进行替换
               if (sourceStr.equalsIgnoreCase(oldStr)){
                   byte[] strBytes = BytesUtils.string2Bytes(newStr);
                   byte[] strLen = BytesUtils.int2Bytes(newStr.length(), u2);
                   // utf8长度的替换
                   classByte = BytesUtils.bytesReplace(classByte, offset - u2, u2, strLen);
                   // utf8内容的替换
                   classByte = BytesUtils.bytesReplace(classByte, offset , len, strBytes);
                   return  classByte;
               }else {
                   offset += len;
               }
               //其他类型的常量都是固定长度
           }else {
              offset += CONSTANT_ITEM_LENTGH[tag];
           }
        }
        return classByte;
    }

    /**
     * 获取字节码文件中，常量池中常量的个数
     * 由于常量池的个数是从1开始计数，所以获取的常量池个数需要减去1
     * 因为0是一个预留的值，标识没有常量
     */
    public int getConstantPoolCont(){
        return BytesUtils.bytes2Int(classByte, CONSTANT_POOL_COUNT_INDEX, u2) - 1;
    }
}
