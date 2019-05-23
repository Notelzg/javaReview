/*
interface UnderGraduate {
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
interface PostDoctoral extends UnderGraduate, Postgraduate, Doctoral{
    void management();
}
public class Worker implements PostDoctoral{
    public void learn(){
        Syetem.out.printle("learin");
    }
    public void test(){
        Syetem.out.printle("test");
    }
    public void research(){
        Syetem.out.printle("research");
    }
    public void experiment(){
        Syetem.out.printle("experiment");
    }
    public void acadamic(){
        Syetem.out.printle("acadamic");
    }
    public void innovation(){
        Syetem.out.printle("innovation");
    }
    public void management(){
        Syetem.out.printle("management");
    }
    public void makeMoney(){
        Syetem.out.printle("makeMoney");
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
    public static void main(String[] args){
        Worker temp = new Worker();
        u(temp);
        v(temp);
        w(temp);
        x(temp);
    }
}
   */
