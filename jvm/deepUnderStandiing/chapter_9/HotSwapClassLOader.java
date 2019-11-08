package deepUnderStandiing.chapter_9;

public class HotSwapClassLOader extends ClassLoader {
    public HotSwapClassLOader(){
        super(HotSwapClassLOader.class.getClassLoader());
    }

    public  Class loadBytes(byte[] bytes){
        return  defineClass(null, bytes, 0, bytes.length);
    }

}
