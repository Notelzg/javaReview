package performance;

import java.util.AbstractList;

public class SlowList extends AbstractList<Integer> {
    private int size = 0;
    private Integer[] elements;
    private int length = 0;

    public SlowList(int length) {
        this.length = length;
        elements = new Integer[length];
    }

    @Override
    public Integer get(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException();
        return elements[index];
    }

    public boolean add(Integer integer){
        elements[size++] = integer;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    public static void main(String[] args){
        SlowList slowList = new SlowList(10);
        for (int i = 0; i < 10; i++)
            slowList.add(i);
        System.out.println(slowList);
    }
}
