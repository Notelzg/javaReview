package chapter10;

import java.io.*;
public class Prace17 {
    interface Wrapping {
        public int value();
    }
    public Wrapping wrapping(){
        return new Wrapping(){
            private int i = 11;
            public int value(){
                return i;
            }
        };
    } 
    public static void main(String[] args){
        Prace17 p = new Prace17();
        Wrapping w = p.wrapping();
        System.out.println(w.value());
    }
}
