package jvm.passiveReference;

/**
 * Usage: <b> 数组定义引用的类，不会初始化 </b>
 *
 * @author lucifer
 *         Date 2016-11-9
 *         Device Aurora R5
 */
public class ArrayReference {
    static {
        System.out.println("Class init.");
    }
}

class Test1 {
    public static void main(String[] args) {
        ArrayReference[] arrayReferences = new ArrayReference[10];
        System.out.println(arrayReferences.length);
    }
}
