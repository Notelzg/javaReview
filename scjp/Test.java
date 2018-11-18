package scjp;


public class Test{
    public static void changeRef(MyDate date){
        date = new MyDate(10, 8, 2018);
    }
    public static void main(String[] args) {
        MyDate mydate = new MyDate(9, 8, 2018);
        mydate.print();
        changeRef(mydate);
        mydate.print();
        MyDate week_next = mydate.addDay(7);
        week_next.print();

    }
}

