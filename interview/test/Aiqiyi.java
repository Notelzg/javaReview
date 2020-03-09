package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Aiqiyi {

    /**
     * 递归冒泡排序
     * 递归冒泡排序
     * 想一下，递归方式
     * 冒泡排序，每次确定一个元素的最终位置
     * 如果是递归的话，升序的话，就是确定谁是最大值，
     * 冒泡排序，小则交换，直到数组的结尾
     */
    public void sortRecur(int[] source) {
        if (source == null || source.length < 2)
            return;
        int len = source.length;
        for (int i = 0; i < len - 1; i++) {
            recur(source, len - i, 0);
        }
    }

    private void recur(int[] source, int len, int start) {
        if (start < len - 1) {
            if (source[start] > source[start + 1]) {
                swap(source, start, start + 1);
            }
            recur(source, len, start + 1);
        }
    }

    private void swap(int[] arr, int source, int target) {
        int temp = arr[source];
        arr[source] = arr[target];
        arr[target] = temp;
    }

    @Test
    public void testSort() {
        int[] t = new int[]{3, 2, 1, 10, 100, 20, 50, 40, 60, 80, 100};
        sortRecur(t);
        for (int i = 0; i < t.length; i++)
            System.out.print(t[i] + " ");
        System.out.println();
    }


}

