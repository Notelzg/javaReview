package test;

public class FindMinTest {
        /**
         *  1，所有字符的字符
         2，所有子串的最短子串
         3， O(n)
         最简单方法for循环寻找各个字符的再S中的位置，然后获取最短的路径
         1 10
         2 9
         5 12
         * @param S string字符串
         * @param T string字符串
         * @return string字符串
         */
        public String minWindow (String S, String T) {
            // write code here
            int sp = 0;
            int ep = 0;
            int[] ar = new int[T.length()];

            for(int i = 0; i < S.length(); i++) {
                for(int j = 0; j < T.length(); j++) {
                    if (S.charAt(i)==T.charAt(j)) {
                        ar[j]++;
                    }
                }
            }
        }
}
