package scjp;

import java.io.*;

public class MyDate{

    private int day;
    private int month;
    private int year;

    public MyDate(){};
    public MyDate(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public MyDate(MyDate date){
        this.day = date.day;
        this.month = date.month;
        this.year = date.year;
    }

    public int getDay(){
        return this.day;
    }
    public void setDay(int day){
        this.day = day;
    }

    public int getMonth(){
        return this.month;
    }
    public void setMonth(int month){
        this.month = month;
    }

    public int getYear(){
        return this.year;
    }
    public void setYear(int year){
        this.year = year;
    }

    public void print(){
        System.out.println("Mydate:  " + year + "-" + month + "-" + day);
    }

    public MyDate addDay(int day){
        MyDate test = new MyDate(this);
        test.day = test.day + day;
 //       test.setDay(this.day+day);
        return test;
    }

}
