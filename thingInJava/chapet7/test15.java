import java.lang.*;

class Person {
    private String name;
    protected final  void set(String name){
        this.name  = name;
    }
    protected   void set1(String name){
        this.name  = name;
    }
    public Person(){}
    public Person(String name ){
        this.name = name; 
    }
    public String toString(){
        return "  Person name is:"+ this.name;
    }
}

class Student extends Person{
    private String id;
    public Student(){
    }
    @Override
    protected  void set1(String id){
        this.id = id;
    }
    public Student(String name, String id){
        super(name);
        this.id = id;
    }
    public void change(String name, String id){
        super.set(name);
        this.id = id;
    }
    public String toString(){
        return " student id is: " + this.id + super.toString(); 
    }
    public static void main(String[] args){
        Student su  = new Student("zgli", "S201507059");
        System.out.println(su.toString());
        su.change("lty", "S2018000");
        System.out.println(su.toString());
        su.set1("S2018000111");
        su.set("jss");
        System.out.println(su.toString());
    }

}

