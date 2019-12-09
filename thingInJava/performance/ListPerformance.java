package performance;

import java.util.*;

public class ListPerformance {
    static Random random = new Random();
    static  int reps = 1000;
    static List<Test<List<Integer>>> tests =
            new ArrayList<>();
    static List<Test<LinkedList<Integer>>> qTests =
            new ArrayList<>();

    static  {
        tests.add(new Test<List<Integer>>("add") {
            @Override
            public int test(List<Integer> container, TestParam tp) {
                int loops = tp.getLoop();
                int listSize = tp.getSize();
                for (int i = 0; i < loops; i++){
                    container.clear();
                    for (int j = 0; j < listSize; j++){
                        container.add(j);
                    }
                }
                return loops * listSize;
            }
        });

        tests.add(new Test<List<Integer>>("get") {
            @Override
            public int test(List<Integer> container, TestParam tp) {
                int loops = tp.getLoop();
                int listSize = tp.getSize();
                for (int i = 0; i < loops; i++){
                    container.get(random.nextInt(listSize));
                }
                return loops;
            }
        });

        tests.add(new Test<List<Integer>>("set") {
            @Override
            public int test(List<Integer> container, TestParam tp) {
                int loops = tp.getLoop();
                int listSize = tp.getSize();
                for (int i = 0; i < loops; i++){
                    container.set(random.nextInt(listSize), 47);
                }
                return loops;
            }
        });
        tests.add(new Test<List<Integer>>("iteradd") {
            @Override
            public int test(List<Integer> container, TestParam tp) {
                final int LOOPS = 1000000;
                int half = container.size() / 2;
                ListIterator<Integer> it = container.listIterator(half);
                for (int i = 0; i < LOOPS; i++) {
                    it.add(47);
                }
                container.clear();
                return LOOPS;
            }
        });
        tests.add(new Test<List<Integer>>("insert") {
            @Override
            public int test(List<Integer> container, TestParam tp) {
                int loops = tp.getLoop();
                for (int i = 0; i < loops; i++){
                    container.add(0, 47);
                }
                return loops;
            }
        });
        tests.add(new Test<List<Integer>>("remove") {
            @Override
            public int test(List<Integer> container, TestParam tp) {
                int loops = tp.getLoop();
                int listSize = tp.getSize();
                for (int i = 0; i < loops; i++){
                    container.clear();
                    Integer[] temp = new Integer[listSize];
                    Arrays.fill(temp,5);
                    container.addAll(Arrays.asList(temp));
                    while (container.size() > 5)
                        container.remove(5);
                }
                return loops * listSize;
            }
        });

        qTests.add(new Test<LinkedList<Integer>>("addFirst"){
            @Override
            public int test(LinkedList<Integer> list, TestParam tp) {
                int loops = tp.getLoop();
                int listSize = tp.getSize();
                for (int i = 0; i < loops; i++){
                    list.clear();
                    for (int j = 0;  j < listSize; j++){
                        list.addFirst(47);
                    }
                }
                return loops * listSize;
            }
        });
        qTests.add(new Test<LinkedList<Integer>>("addLast"){
            @Override
            public int test(LinkedList<Integer> list, TestParam tp) {
                int loops = tp.getLoop();
                int listSize = tp.getSize();
                for (int i = 0; i < loops; i++){
                    list.clear();
                    for (int j = 0;  j < listSize; j++){
                        list.addLast(47);
                    }
                }
                return loops * listSize;
            }
        });
        qTests.add(new Test<LinkedList<Integer>>("rmFirst"){
            @Override
            public int test(LinkedList<Integer> list, TestParam tp) {
                int loops = tp.getLoop();
                int listSize = tp.getSize();
                for (int i = 0; i < loops; i++){
                    list.clear();
                    Integer[] temp = new Integer[listSize];
                    Arrays.fill(temp,5);
                    list.addAll(Arrays.asList(temp));
                    while (list.size() > 0)
                        list.removeFirst();
                }
                return loops * listSize;
            }
        });
        qTests.add(new Test<LinkedList<Integer>>("removeLast"){
            @Override
            public int test(LinkedList<Integer> list, TestParam tp) {
                int loops = tp.getLoop();
                int listSize = tp.getSize();
                for (int i = 0; i < loops; i++){
                    list.clear();
                    Integer[] temp = new Integer[listSize];
                    Arrays.fill(temp,5);
                    list.addAll(Arrays.asList(temp));
                    while (list.size() > 0)
                        list.removeLast();
                }
                return loops * listSize;
            }
        });
        qTests.add(new Test<LinkedList<Integer>>("getFirst"){
            @Override
            public int test(LinkedList<Integer> list, TestParam tp) {
                int loops = tp.getLoop();
                int listSize = tp.getSize();
                for (int i = 0; i < loops; i++){
                    list.clear();
                    Integer[] temp = new Integer[listSize];
                    Arrays.fill(temp,5);
                    list.addAll(Arrays.asList(temp));
                    for (int j = 0;  j < listSize; j++){
                        list.getFirst();
                        list.removeFirst();
                    }
                }
                return loops * listSize;
            }
        });
        qTests.add(new Test<LinkedList<Integer>>("getLast"){
            @Override
            public int test(LinkedList<Integer> list, TestParam tp) {
                int loops = tp.getLoop();
                int listSize = tp.getSize();
                for (int i = 0; i < loops; i++){
                    list.clear();
                    Integer[] temp = new Integer[listSize];
                    Arrays.fill(temp,5);
                    list.addAll(Arrays.asList(temp));
                    for (int j = 0;  j < listSize; j++){
                        list.getLast();
                        list.removeLast();
                    }
                }
                return loops * listSize;
            }
        });
    }
    static  class ListTester extends Tester<List<Integer>>{
        public ListTester(List<Integer> container,
                          List<Test<List<Integer>>> tests){
            super(container, tests);
        }
        @Override
        protected List<Integer> initialize(int size){
            Integer[] temp = new Integer[size];
            Arrays.fill(temp,5);
            this.container.addAll(Arrays.asList(temp));
            return  this.container;
        }
        public static void run(List<Integer> list,
                               List<Test<List<Integer>>> tests){
            new ListTester(list, tests).timeTest();
        }

    }
    public  static  void main(String[] args){
        Tester<List<Integer>> tester = new Tester<List<Integer>>(
                new ArrayList<Integer>(), tests);
           tester.timeTest();

        Tester<List<Integer>> arrayTest =
                new Tester<List<Integer>>(null, tests.subList(1,3)){
            @Override
            protected List<Integer> initialize(int size){
                Integer[] temp = new Integer[size];
                Arrays.fill(temp,5);
                return  (Arrays.asList(temp));
            }
           };
        arrayTest.setHeadLine("Array as list");
        arrayTest.timeTest();
        ListTester.run(new ArrayList<Integer>(),tests );
        ListTester.run(new LinkedList<Integer>(),tests );
        Tester<LinkedList<Integer>> qTest =
                new Tester<LinkedList<Integer>>(
                        new LinkedList<Integer>(), qTests);
        qTest.timeTest();
    }
}
