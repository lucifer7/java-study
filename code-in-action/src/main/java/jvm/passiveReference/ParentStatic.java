package jvm.passiveReference;

/**
 * Usage: <b> 通过子类引用父类的静态字段，不会初始化子类 </b>
 *
 * @author lucifer
 *         Date 2016-11-9
 *         Device Aurora R5
 */
public class ParentStatic {
    static {
        System.out.println("Super class initialized.");
    }

    public static int value = 123;

}

class SubClass extends ParentStatic {
    static {
        System.out.println("Sub class initialized.");
    }
}

class Test {
    public static void main(String[] args) {
        System.out.println(SubClass.value);
    }
}