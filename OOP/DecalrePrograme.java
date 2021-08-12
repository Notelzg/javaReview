import java.io.PrintStream;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * 声明试编程
 */
public class DecalrePrograme {
    Proxy

    static  Logger logger =  Logger.getLogger(DecalrePrograme.class.getName());
    private interface A {
        public int valueA(String s);
    }
    private interface B {
        public int valueB(String s);
    }
    static  class Car {
        public String make;
        private String model;
        private int year;

        public Car(String theMake, String theModel, int yearOfMake) {
            make = theMake;
            model = theModel;
            year = yearOfMake;
        }

        public String getMake() { return make; }
        public String getModel() { return model; }
        public int getYear() { return year; }

        @Override
        public String toString() {
            return "\nCar{" +
                    "make='" + make + '\'' +
                    ", model='" + model + '\'' +
                    ", year=" + year +
                    '}';
        }
    }
    public static void main(String[] args){

        Map<String, Integer> pageVisit = new HashMap<>();
       String page = "wwww.test.com";
//       incrementPage(pageVisit, page);
//       incrementPage(pageVisit, page);
       dIncreamentPage(pageVisit, page);
       dIncreamentPage(pageVisit, page);
        System.out.println(pageVisit.get(page));
        List<Car> list = createCars();
        sort(list);
        list = new ArrayList<>();
        list.add( new Car("Jeep", "Wrangler", 2011));
        list.add(new Car("Jeep", "Comanche", 1990));
        list.add(new Car("Dodge", "Avenger", 2010));
        list.add(new Car("Buick", "Cascada", 2016));
        list.add(new Car("Ford", "Focus", 2012));
        list.add(new Car("Chevrolet", "Geo Metro", 1992));
        Predicate<Car> pred1 = key->"Jeep".equalsIgnoreCase(key.getMake());
        Predicate<Car> pred2 = car -> "Dodge".equals(car.getMake());
//        list.removeIf(pred1.or(pred2));
        list.stream().filter(pred1.and(pred2)).forEach(key-> System.out.println(key));
        System.out.println(list);
        A a = String::length;
        B b = String::length;
        // compiler error!
        // b = a;
        // ClassCastException at runtime!
        // b = (B)a;
        // works, using a method reference
        b = a::valueA;
        System.out.println(b.valueB("abc"));
        ExecutorService executorService = Executors.newFixedThreadPool(10);

//        IntStream.range(0, 5)
//                .forEach(i ->
//                        executorService.submit(new Runnable() {
//                            public void run() {
//                                System.out.println("Running task " + i);
//                            }
//                        }));
//
//        executorService.shutdown();
        IntStream.rangeClosed(1,10).forEach(i->executorService.submit(()-> System.out.println(i)));
        executorService.shutdown();
        Function<Integer, Predicate<Integer>> isGreaterThan = (pivot) ->  (candidate) -> candidate > pivot ;
        list.stream().map(Car::getYear).filter(isGreaterThan.apply(2011)).forEach(System.out::println);
        logger.info("this is test");



    }

    /**
     * 命令式编程
     * @param list
     * @return
     */
     public static List<String> sort(List<Car> list){
         List<Car>  yaearFilter = new ArrayList<>();
         // filter
         for (Car car : list){
             if (car.getYear() > 2000)
                 yaearFilter.add(car);
         }
        // sorter
         Collections.sort(yaearFilter, new Comparator<Car>() {
             @Override
             public int compare(Car o1, Car o2) {
                 return new Integer(o1.getYear()).compareTo(o1.getYear());
             }
         });

         List<String> models = new ArrayList<>();
         for(Car car : yaearFilter) {
             models.add(car.getModel());
         }
        return models;
     }

    /**
     * 声明式编程
     * @return
     */
    public static List<String>  dSort(List<Car> list){
        return  list.stream()
                .filter((car -> car.getYear() > 2000))
                .sorted(Comparator.comparing(Car::getYear))
                .map(car->car.getModel())
                .collect(Collectors.toList());
    }

    public static List<Car> createCars() {
        return Arrays.asList(
                new Car("Jeep", "Wrangler", 2011),
                new Car("Jeep", "Comanche", 1990),
                new Car("Dodge", "Avenger", 2010),
                new Car("Buick", "Cascada", 2016),
                new Car("Ford", "Focus", 2012),
                new Car("Chevrolet", "Geo Metro", 1992)
        );
    }
    /**
     * 命令式变成,是what and how
     * @param map
     * @param page
     */
    public static void incrementPage(Map<String, Integer> map,  String page){

        if (!map.containsKey(page))
            map.put(page,1);
        else
            map.put(page, map.get(page) + 1);
    }
    /**
     * 声明试编程,是把how,改为what,寻找已有的内置函数来完成
     */
    public static void dIncreamentPage(Map<String, Integer> map,  String page){
        map.merge(page,1, (key, value)-> value + 1);
    }
}
