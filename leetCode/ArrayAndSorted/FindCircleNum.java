package ArrayAndSorted;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 班上有 N 名学生。其中有些人是朋友，有些则不是。他们的友谊具有是传递性。如果已知 A 是 B 的朋友，B 是 C 的朋友，
 * 那么我们可以认为 A 也是 C 的朋友。所谓的朋友圈，是指所有朋友的集合。
 * <p>
 * 给定一个 N * N 的矩阵 M，表示班级中学生之间的朋友关系。
 * 如果M[i][j] = 1，表示已知第 i 个和 j 个学生互为朋友关系，否则为不知道。你必须输出所有学生中的已知的朋友圈总数。
  思路1： 暴力解法 因为有n个学生，所以dfs算法最多被调用n次，dfs在两个for循环中都有的，但是加起来运行次数为节点的次数
  dfs里面有一个for循环，因为dfs最多运行n次，所有时间复杂度是 O(n*n) = O(n^2),空间复杂度是递归n次是常数所以是 O(1)
  因为 M[i][i] =1, 代表一名学生，从每个学生进行深度遍历，把每个遍历过的节点设置为0，
  看看最多经过多少次深度遍历，所有节点都为0，则证明有多少个朋友圈
 */
public class FindCircleNum {
    /**
     * 深度遍历
     * @param m   矩阵
     * @param row 行下标，还有一个row就够了，因为节点是 m【row][row]
     */
    private void dfs(int[][] m, int row) {
        if (m[row][row] == 1) {
            //该学生已经有圈子了
            m[row][row] = 0;
            //遍历某个未访问过的朋友,
            for (int i = 0; i < m.length; i++){
                if (m[i][i] == 1 && m[row][i] == 1)
                    dfs(m, i);
            }
        }
    }

    public int findCircleNum(int[][] M) {
        int len = M.length;
        if (len != M[0].length)
            return 0;
        if (len < 2)
            return len;
        int ans = 0;
        // 循环所有未被访问过的学生
        for (int i = 0; i < len; i++) {
            if (M[i][i] == 0)
                continue;
            dfs(M, i);
            ans++;
        }
        return ans;
    }

    @Test
    public void test() {
        Assertions.assertEquals(1, findCircleNum(new int[][]{
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}}
        ));
        Assertions.assertEquals(1, findCircleNum(new int[][]{
                {1, 0, 0, 1},
                {0, 1, 1, 0},
                {0, 1, 1, 1},
                {1, 0, 1, 1}
        }));
        Assertions.assertEquals(0, findCircleNum(new int[][]{{1, 1, 0, 0}}));
        Assertions.assertEquals(1, findCircleNum(new int[][]{{1}}));
        Assertions.assertEquals(3, findCircleNum(new int[][]{
                {1, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        }));
        Assertions.assertEquals(4, findCircleNum(new int[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        }));
        Assertions.assertEquals(2, findCircleNum(new int[][]{
                {1, 1, 0},
                {1, 1, 0},
                {0, 0, 1}
        }));
        Assertions.assertEquals(1, findCircleNum(new int[][]{
                {1, 1, 0},
                {1, 1, 1},
                {0, 1, 1}
        }));
    }
}
