package level_easy;

import ListAanTree.utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * UTF-8 中的一个字符可能的长度为 1 到 4 字节，遵循以下的规则：
 * <p>
 * 对于 1 字节的字符，字节的第一位设为0，后面7位为这个符号的unicode码。
 * 对于 n 字节的字符 (n > 1)，第一个字节的前 n 位都设为1，第 n+1 位设为0，后面字节的前两位一律设为10。剩下的没有提及的二进制位，全部为这个符号的unicode码。
 * 这是 UTF-8 编码的工作方式：
 * <p>
 * Char. number range  |        UTF-8 octet sequence
 * (hexadecimal)    |              (binary)
 * --------------------+---------------------------------------------
 * 0000 0000-0000 007F | 0xxxxxxx
 * 0000 0080-0000 07FF | 110xxxxx 10xxxxxx
 * 0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx
 * 0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
 * 给定一个表示数据的整数数组，返回它是否为有效的 utf-8 编码。
 * <p>
 * 注意:
 * 输入是整数数组。只有每个整数的最低 8 个有效位用来存储数据。这意味着每个整数只表示 1 字节的数据。
 */
public class UTF8Check {
    public boolean validUtf8(int[] data) {
        if (data == null || data.length == 0)
            return false;
        int len = data.length;
        if (len == 1) {
            return (data[0] & 0x80) == 0;
        }
        //先判断是几个字符的
        int index = 0;
        int temp;
        while (index < len) {
            int uLen = isUtf((short)data[index]);
            temp = index;
            index = index + uLen ;
            if (uLen == 0)
                return false;
            if (uLen == 1)
               continue;
            for (int i = temp + 1; i < index; i++) {
                if ((data[i] & 0x80) == 0 ||  (data[i] & 0x40) != 0)
                    return false;
            }
        }
        return true;
    }

    private int isUtf(short data) {
        if ((data & 0x80) == 0)
            return 1;
        if ((data & 0xc0) == 0xc0 && (data & 0x20 ) == 0)
            return 2;
        if ((data & 0xe0) == 0xe0 && (data &0x10 ) == 0)
            return 3;
        if ((data & 0xf0) == 0xf0 && (data & 0x08) == 0)
            return 4;
       return 0;
    }

    @Test
    public void test() {
        Assertions.assertEquals(true, validUtf8(utils.str2intArr("1")));
        Assertions.assertEquals(true, validUtf8(utils.str2intArr("197, 130, 1")));
        Assertions.assertEquals(false, validUtf8(utils.str2intArr("235, 140, 4")));
    }
}
