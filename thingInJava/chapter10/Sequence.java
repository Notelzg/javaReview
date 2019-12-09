package chapter10;

import java.lang.*;
interface Selector{
boolean end();
Object current();
void next();
}
public class Sequence {
    private Object[] items;
    private int next = 0;
    public Sequence () { }
    public Sequence (int size)
    {
    items = new Object[size];
    }
    public void add(Object item){
    items[next++] = item;
    }
    private class SequenceSelector implements Selector{
        private int i = 0;
        public Object current(){
            return items[i];
        }
        public boolean end(){
            return i == items.length;
        }
        public void next(){
            if (i < items.length) i++;
        }
        public Sequence getSequence(){
            return Sequence.this;
        }
    }
    public Selector selector(){
        return new SequenceSelector();
    }
    public int getNext(){
        return  next;
    }
    public static void main(String[] args){
       Sequence sequence =  new Sequence(10);
       for (int i = 0; i< 10; i++)
            sequence.add(new Practice2_1(i + ""));
       Selector selector = sequence.selector();
       Sequence.SequenceSelector ss =  sequence.new SequenceSelector();
       Sequence test = ss.getSequence();
        System.out.println(test.getNext());
       while(!selector.end()){
            System.out.println(selector.current() + "  ");
            selector.next();
       }
    }
}
