package chapter15;

import java.lang.*;
import java.io.*;
public class test5<T>{
    private class Node{
       Node(){
       //Class<?> name = T.getClass(); 
       //System.out.println(name);
       System.out.println(test5.this.getClass());
        }
    }
    public String toString(){
        Node t = new Node();
        return  null;
    }
    public <A, B, C>  void f(A a, B b, C c, int d){
       System.out.println(d);
       System.out.println(a.getClass().getName());
       System.out.println(b.getClass().getName());
       System.out.println(c.getClass().getName());
    }
    public static void main(String[] args){
        test5<String> t = new test5<String>();
        t.toString();
        t.f("a", 1, 2.2, 4);
    }
}
