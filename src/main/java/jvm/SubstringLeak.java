package jvm;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/7/8
 * Time: 11:33
 **/
// test in jdk6
public class SubstringLeak {
    private String largeString = new String(new byte[100000]);

    public static void main(String[] args) {
        java.util.ArrayList list = new java.util.ArrayList();
        for (int i = 0; i < 1000000; i++) {
            SubstringLeak gc = new SubstringLeak();
            list.add(gc.getString());
        }
    }

    String getString() {
        return this.largeString.substring(0, 2);
    }
}