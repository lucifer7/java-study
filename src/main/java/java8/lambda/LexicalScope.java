package java8.lambda;

/**
 * Usage: <b> Lambda v.s. anonymous inner class: Lexical scoping(词法作用域) </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/4/5
 **/
public class LexicalScope {
    Runnable r1 = () -> System.out.println(this);
    Runnable r2 = () -> System.out.println(toString());

    public String toString() {  return "Hello, world"; }

    public static void main(String[] args) {
        new LexicalScope().r1.run();
        new LexicalScope().r2.run();
        new Runnable() {
            @Override
            public void run() {
                System.out.println(this);
                System.out.println(toString());
            }
        }.run();
    }
}
