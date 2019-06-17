package chapter19_Enum;

import sun.jvm.hotspot.oops.OopUtilities;

enum OutCome{
    GT, EQ, LT
}
interface Item{
    OutCome compete(Item item);
    OutCome eval(AA aa);
    OutCome eval(BB bb);

}
class AA implements Item{
    public AA() {
        super();
    }

    @Override
    public OutCome compete(Item item) {
        return item.eval(this);
    }

    @Override
    public OutCome eval(AA aa) {
        System.out.println("aa vs aa ");
        return OutCome.EQ;
    }

    @Override
    public OutCome eval(BB bb) {
        System.out.println("aa vs bb ");
        return OutCome.GT;
    }
}
class  BB implements Item{
    @Override
    public OutCome compete(Item item) {
        return item.eval(this);
    }

    @Override
    public OutCome eval(AA aa) {
        System.out.println("bb vs aa ");
        return OutCome.LT;
    }

    @Override
    public OutCome eval(BB bb) {
        System.out.println("bb vs bb ");
        return OutCome.EQ;
    }
}
public class Practice_19 {
    public static void main(String[] args){
        Item aa = new AA();
        Item bb = new BB();
        aa.compete(bb);
        bb.compete(bb);
        bb.compete(aa);
    }
}
