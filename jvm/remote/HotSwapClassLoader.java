package remote;

public class HotSwapClassLoader extends  ClassLoader{

    public  HotSwapClassLoader(){
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] classType){
        return defineClass(null, classType, 0, classType.length);
    }

}
