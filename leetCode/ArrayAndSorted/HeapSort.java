package ArrayAndSorted;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

/**
 * 堆排序
 * 插入 需要调整
 * 删除 需要调整
 * 建堆 根据一个已有的数组建堆
 * 两种方式 ，1， 调用 插入一个个插入
 * 2， 从最后一个叶子节点的父亲节点开始调整堆，
 */
public class HeapSort {
    private int[] arr;
    private int size;
    /**
     * 从当前节点到叶子节点进行调整，适用于建堆和删除
     */
    public void siftDown(int current,int len) {
        int value = arr[current];
        int half = len >>> 1;
        while (current < half) {
            int child = (current << 1) + 1;
            int right = child + 1;
            if (right < arr.length && arr[child] > arr[right])
                child = right;
            if (value < arr[child])
                break;
            arr[current] = arr[child];
            current = child;
        }
        arr[current] = value;
    }

    /**
     * 从当前节点到根节点进行调整，适用插入节点
     */
    public void siftUp(int value) {
        if (size == arr.length){
            int[] newArr = new int[arr.length << 1];
            System.arraycopy(arr, 0, newArr,0, arr.length);
            this.arr = newArr;
        }
        int current = size++;
        while (current > 0) {
            int parent = (current - 1) >>> 1;
            if (value > arr[parent])
                break;
            arr[current] = arr[parent];
            current = parent;
        }
        arr[current] = value;
    }

    public void heapify(){
        size = arr.length;
        int half = arr.length >>> 1;
        int k = half - 1;
        while (k >= 0){
            siftDown(k--, arr.length);
        }
    }

    @Test
    public void test() {
        HeapSort heapSort = new HeapSort();
        heapSort.arr = new int[]{5,4,3,2,1};
        heapSort.heapify();
        heapSort.siftUp(-1);
        heapSort.siftUp(109);
        int min;
        for (int i = 0; i < heapSort.size ; i++){
            int len = heapSort.size - 1;
            System.out.println(heapSort.arr[0]);
            heapSort.arr[0] = heapSort.arr[len -i];
            heapSort.siftDown(0, len - i);
        }

    }
}
