package util;

import org.junit.Test;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/4/14
 **/
public class FormatterTest {
    @Test
    public void toPlainStringDouble() throws Exception {
        Double d = -2.58E-4;
        //Double e = 36.121167570012348;
        Double e = 0.03433271486561462;
        Double f = -115.169645550012342d;
        Double g = 113.52351268001234;
        System.out.println(Formatter.toPlainString(d));
        System.out.println(Formatter.toPlainString(e));
        System.out.println(Formatter.toPlainString(f));
        System.out.println(Formatter.toPlainString(g));
        System.out.println(String.format("%.16f", g));

    }

    @Test
    public void toPlainString1() throws Exception {
        Float g = 113.52f;
        Float h = 123456789f;
        System.out.println(Formatter.toPlainString(g));
        System.out.println(String.format("%.16f", g));
        System.out.println(Formatter.toPlainString(h));
        System.out.println(String.format("%f", h));

    }

}