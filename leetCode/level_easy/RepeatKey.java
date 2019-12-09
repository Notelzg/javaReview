package level_easy;

import com.sun.javafx.stage.StagePeerListener;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 一个有序、有重复的数组，查找一个关键字，如果该关键字是重复的，则返回该关键字的所有下标
 * 否则返回单个下标。
 */
public class RepeatKey {
    public String solution(int[] arr, int key){
        List<Integer> list = new ArrayList<>();
        if (arr == null){
            return " ";
        }
        Arrays.sort(arr);
        if (arr[0] > key || arr[arr.length - 1] < key)
            return "";
        /* 如果数组数据全部一样 */
        if (arr[0] == arr[arr.length - 1] && arr[0] == key){
            return  IntStream.range(0, arr.length).boxed().map(k->k.toString()).collect(Collectors.joining(", "));
        }
        boolean flag = false;
        for (int i = 0; i < arr.length; i++){
             if (key == arr[i]) {
                 list.add(i);
                 flag = true;
             }else if (flag == true){
                 break;
             }
        }
        return list.stream().map(key1->key1.toString()).collect(Collectors.joining(", "));
    }

    public String solution2(int[] arr, int key){
        List<Integer> list = new ArrayList<>();
        if (arr == null){
            return " ";
        }
        Arrays.sort(arr);
        if (arr[0] > key || arr[arr.length - 1] < key)
            return "";
        /* 如果数组数据全部一样 */
        if (arr[0] == arr[arr.length - 1] && arr[0] == key){
            return  IntStream.range(0, arr.length).boxed().map(k->k.toString()).collect(Collectors.joining(", "));
        }
        int left = 0;
        int right = arr.length - 1;
        while (left <= right){
            int middle = (left + right)/2;
            if (key == arr[middle]){
                int temp = middle;
               while (arr[--temp] == key){
                  list.add(temp);
               }
               list.add(middle);
                while (arr[++middle] == key){
                    list.add(middle);
                }
                break;
            }else  if (key > arr[middle]){
                left = middle + 1;
            }else {
                right = middle - 1;
            }
        }
        return list.stream().map(key1->key1.toString()).collect(Collectors.joining(", "));
    }
    @Test
    public void test(){
        System.out.println(solution2(new int[]{1, 3, 5, 5, 7}, 5));
        System.out.println(solution2(new int[]{5, 5, 5, 5, 5}, 5));
    }
}
