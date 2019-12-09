package chapetr9;

import java.io.*;
import java.lang.*;
interface UnderGraduate {
    int TEST = 0;
   void learn();
   void test();
}

interface PostGraduate{
    void research();
    void experiment();
}
interface Doctoral{
   void acadamic();
   void innovation();
}
interface PostDoctoral extends UnderGraduate, PostGraduate, Doctoral{
    void management();
}
abstract class Teacher implements PostDoctoral{
    abstract void report();
    public void learn(){
        System.out.println("learin");
    }
    public void test(){
        System.out.println("test");
    }
    public void research(){
        System.out.println("research");
    }
    public void experiment(){
        System.out.println("experiment");
    }
    public void acadamic(){
        System.out.println("acadamic");
    }
    public void innovation(){
        System.out.println("innovation");
    }
    public void management(){
        System.out.println("management");
    }
}
//class Student extends Worker{
//    public void report(){
//        System.out.println("report");
//    }
//}
public class Worker extends Teacher{
    public void makeMoney(){
        System.out.println("makeMoney");
    }
    public void report(){
        System.out.println("report");
    }
    static void u(UnderGraduate u){
        u.learn();
        u.test();
    }
    static void v(PostGraduate u){
        u.research();
        u.experiment();
    }
    static void w(Doctoral u){
        u.acadamic();
        u.innovation();
    }
    static void x(PostDoctoral u){
        u.management();
    }
    static void y(Teacher u){
        u.report();
    }
    public static void main(String[] args){
        System.out.println(UnderGraduate.TEST);
//        UnderGraduate.TEST++;
        System.out.println(UnderGraduate.TEST);
        //Worker temp = new Worker();
        Teacher temp = new Worker();
        u(temp);
        w(temp);
        x(temp);
        y(temp);
    }
}
