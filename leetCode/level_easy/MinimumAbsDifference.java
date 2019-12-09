package level_easy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 给你个整数数组 arr，其中每个元素都 不相同。
 *
 * 请你找到所有具有最小绝对差的元素对，并且按升序的顺序返回。
 * 输入：arr = [4,2,1,3]
 * 输出：[[1,2],[2,3],[3,4]]
 */
public class MinimumAbsDifference {
    public static  List<List<Integer>> solution(int[] arr) {
        List<List<Integer>>   listList = new ArrayList<>();
        if (null == arr || arr.length < 2)
            return listList;
        Arrays.sort(arr);
        int min = Integer.MAX_VALUE;
        int abs ;
        for (int i = 0; i < arr.length - 1; i++){
            abs = arr[i+1] - arr[i];
            if (abs < min)
                 min = abs;
        }
        for (int i = 0; i < arr.length - 1; i++){
            abs = arr[i+1] - arr[i];
            if (abs == min)
                listList.add(Arrays.asList(arr[i], arr[i+1] ));
        }
        return listList;
    }

    @Test
    public void test(){
//       MinimumAbsDifference.solution(new int[]{4, 2 , 1, 3}).forEach(System.out::println);
//       MinimumAbsDifference.solution(new int[]{1,3,6,10,15}).forEach(System.out::println);
//       MinimumAbsDifference.solution(new int[]{3,8,-10,23,19,-4,-14,27}).forEach(System.out::println);
       MinimumAbsDifference.solution(new int[]{3,8,-10,23,19,-4,-14,27}).forEach(System.out::println);

    }
}
