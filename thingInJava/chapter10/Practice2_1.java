package chapter10;

public class Practice2_1 {
    private String str;
    public Practice2_1(){
    }
    public Practice2_1(String para){
        this.str = para;
    }
    private class ToString{
        public String toString(){
            return str + " double  " + str ;
        }
    }
    public ToString getToString(){
        return new ToString();
    }
    public String toString(){
        return str + " double  " + str ;
    }
    public static void main(String[] args){
        Practice2_1 p = new Practice2_1("zgli");
        System.out.println(p.getToString().toString()); 
    }
}
