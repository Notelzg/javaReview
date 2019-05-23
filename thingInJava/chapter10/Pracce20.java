public class Prace18 {
    class Wrapping {
        int value;
    }
    public Wrapping wrapping(int x){
        return new Wrapping(x){
            public int value(){
                return super.value()* 47;
            }
        };
    } 
    public static void main(String[] args){
        Parce18 p = new Parce18();
        Wrapping w = p.Wrapping(10);
    }
}
