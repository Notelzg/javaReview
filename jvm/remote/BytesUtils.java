package remote;

public class BytesUtils {
    /**
     * notice
     * @param b
     * @param start
     * @param len
     * @return
     */
    public static int bytes2Int(byte[] b, int start, int len){
       int sum = 0;
       int end = start + len;
       for (int i = len -1; i >= 0; i--){
           // BigEdin存储的方法，数组下标0开始存储，最高位的值, 通过在int类型的4字节长度，进行移位，
           // 在移位之前需要先转换为int类型的原因是，需要扩充空间为了移位做准备
           // 做移位之前要保证int前8个字节和byte[i]是保持一致的，剩下的24个字节置为0,所以 &0xff，0xFF自动向上转型用0补齐
           //  前24字节，0xff 相当于 0x00 00 00 ff，两种写法表达意思相同，java里面应该是自动转为左边的int类型的长度
           int temp = ((int)b[start++]) & 0xff;
           temp = temp << (i * 8);
           sum = temp + sum;
       }
       return  sum;
    }

    /**
     * notice
     * @param value
     * @param len
     * @return
     */
    public static byte[] int2Bytes(int value, int len){
        byte[] bytes = new byte[len];
        int j = 0;
        for (int i = len - 1; i >= 0; i--){
            // BigEdin存储的方法，所以高位的值需要存储到左边，即从数组下标0开始存储，最高位的值
            bytes[j++] = (byte)((value >> i * 8) & 0xff);
        }
        return bytes;
    }

    public static String bytes2String(byte[] b, int start, int len){
       return  new String(b, start, len);
    }

    public static byte[] string2Bytes(String str){
        return str.getBytes();
    }

    /**
     * 根据要复制数组的长度，在原数组的长度的基础上进行处理，如果要替换的字符串长度大于被替换字符串长度则需要增加newBytesde长度
     * 否则需要减小数组的长度。
     * 开始复制，先处理orginal中的前端和后端，最后把要复制的字符串放到中间就可以了。
     *      original                newBytes
     * 1，复制o-->offset的bytes到 0-->offset，这个范围的值不变
     * 2，复制offset+len-->original.length到 offset+replaceBytes.length-->newBytes.length ， 这个范围的也不变
     * 3，复制replaceBytes到         offset-->offset+replace.length
     *
     * @param originalBytes
     * @param offset   被替换字符串的偏移量
     * @param len      被替换字符串的长度
     * @param replaceBytes  新的字符串
     * @return
     */
    public static byte[] bytesReplace(byte[] originalBytes, int offset, int len, byte[] replaceBytes){
        byte[] newBytes = new byte[originalBytes.length + (replaceBytes.length - len)];
        System.arraycopy(originalBytes, 0, newBytes, 0, offset); ;
        System.arraycopy(originalBytes, offset + len , newBytes, offset + replaceBytes.length, originalBytes.length -offset - len); ;
        System.arraycopy(replaceBytes, 0, newBytes, offset, replaceBytes.length);
        return  newBytes;
    }
    public static void main(String[] args){
        byte b = -2;
        System.out.format("%x\n", b);
        int temp = (int)b;
        System.out.format("%010x \n", temp);
        System.out.format("%010x \n", temp & 0xff);
        System.out.format("%010x \n", 0xff);
    }
}
