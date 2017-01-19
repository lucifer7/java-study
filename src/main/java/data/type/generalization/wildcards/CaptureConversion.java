package data.type.generalization.wildcards;


import lombok.extern.log4j.Log4j;

/**
 * Usage: <b> Both produces no warning, no error, same output </b>
 * WHY is this different from Thinking in Java 15.10.4 ?
 *
 * @author lucifer
 *         Date 2017-1-16
 *         Device Aurora R5
 */
@Log4j
public class CaptureConversion {
    static <T> void f1(Holder<T> holder) {
        T t = holder.getValue();
        log.info(t.getClass().getSimpleName());
    }

    static void f2(Holder<?> holder) {
        f1(holder);
    }

    public static void main(String[] args) {
        Holder raw = new Holder<Integer>(1);
        f1(raw);
        f2(raw);

        Holder rawBasic = new Holder();
        rawBasic.setValue(new Object());
        f1(rawBasic);
        f2(rawBasic);

        Holder<?> wildcarded = new Holder<Double>(1.0);
        f1(wildcarded);
        f2(wildcarded);
    }
}
