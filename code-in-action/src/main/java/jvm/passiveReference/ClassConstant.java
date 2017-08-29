package jvm.passiveReference;

/**
 * Usage: <b> 引用类常量，类不会初始化 </b>
 *
 * @author lucifer
 *         Date 2016-11-9
 *         Device Aurora R5
 */
public class ClassConstant {
    public static final int CONT = 123;

    static {
        System.out.println("Class init.");
    }
}

class Test2 {
    public static void main(String[] args) {
        System.out.println(ClassConstant.CONT);
    }
}
