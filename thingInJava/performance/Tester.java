package performance;

import java.util.List;

public class Tester<C> {
    public  static  int fieldWidth = 8;
    public static TestParam[] defaultParams = TestParam.array(
       10, 5000, 100, 5000, 1000, 5000, 10000, 500);
    protected C container;
    protected C initialize(int size){
        return container;
    }
    private  String headLine = "";
    private List<Test<C>> testList;
    private static String stringField(){
        return "%" + fieldWidth + "s";
    }
    private static String numberField(){
        return  "%" + fieldWidth + "d";
    }
    private  static  int sizeWidth = 5;
    private  static  String sizeField = "%" + sizeWidth + "s";
    private  TestParam[]  paramList = defaultParams;
    public  Tester(C container, List<Test<C>> tests){
        this.container = container;
        this.testList = tests;
        if (null != container)
            headLine = container.getClass().getSimpleName();
    }

    public Tester(C container, List<Test<C>> testList, TestParam[] testParams) {
        this(container, testList);
        this.paramList = testParams;
    }

    public  void setHeadLine(String headLine){
        this.headLine = headLine;
    }

    public static <C> void run(C container, List<Test<C>> testList){
        new Tester<>(container,testList);
    }
    public static <C> void run(C container, List<Test<C>> testList, TestParam[] testParams){
        new Tester<>(container,testList, testParams);
    }
    private  void dispalyHeaer(){
        int width = fieldWidth * testList.size() + sizeWidth;
        int dashLength = width - headLine.length() - 1;
        StringBuilder head = new StringBuilder(width);
        for (int i = 0; i < dashLength/2; i++)
            head.append("-");
        head.append(" ");
        head.append(headLine);
        for (int i = 0; i < dashLength/2; i++)
            head.append("-");
        System.out.println(head);
        System.out.format(sizeField, "size");
        for (Test<C> test : testList)
            System.out.format(stringField(), test.name);
        System.out.println();

    }

    public void timeTest(){
        dispalyHeaer();
        for (TestParam param : paramList){
            System.out.format(sizeField, param.getSize());
            for (Test<C> test : testList){
                C kontainer = initialize(param.getSize());
                long start = System.nanoTime();
                int reps = test.test(kontainer, param);
                long duration = System.nanoTime()  - start;
                long timePerRep = duration / reps;
                System.out.format(numberField(), timePerRep);
            }
            System.out.println();
        }
    }
}
