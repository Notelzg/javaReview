package performance;

public abstract  class Test<C> {
    String name;
    public Test(String name){
        this.name = name;
    }
    abstract int test(C contanier, TestParam testParam);
}
