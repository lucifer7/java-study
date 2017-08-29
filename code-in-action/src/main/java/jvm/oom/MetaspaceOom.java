package jvm.oom;

/**
 * Usage: <b> Produce OOM of Metaspace  </b>
 * VM arg: -XX:+PrintGCDetails
 *
 * @author Jingyi.Yang
 *         Date 2017/2/6
 * @since jdk 8
 **/
public class MetaspaceOom {
    static javassist.ClassPool cp = javassist.ClassPool.getDefault();

    public static void main(String[] args) throws Exception{
        for (int i = 0; ; i++) {
            Class c = cp.makeClass("eu.plumbr.demo.Generated" + i).toClass();
        }
    }
}
