package specification.ch6names;

/**
 * Usage: <b> Simple Method Names and Visibility </b>
 * Comb rule: search a nested class's super class hierarchy before the lexicall enclosing scope
 *
 * @author Jingyi.Yang
 *         Date 2017/4/27
 **/
public class MethodNameVisibility {
}
class Super {
    void f2(String s)       {}
    void f3(String s)       {}
    void f3(int i1, int i2) {}
}

class Test {
    void f1(int i) {}
    void f2(int i) {}
    void f3(int i) {}

    void m() {
        new Super() {
            {
                f1(0);  // OK, resolves to Test.f1(int)
                //f2(0);  // compile-time error, cannot applied to Super.f2(), and Test is not searched, even Test.f2() is visible
                //f3(0);  // compile-time error, Note that Test is not searched, either
            }
        };
    }
}