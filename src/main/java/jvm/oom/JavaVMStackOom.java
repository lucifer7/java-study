package jvm.oom;

/**
 * Usage: <b> Test vm stack OuterOfMemoryError </b>
 * VM args: -Xss2m
 *
 * DO NOT TEST ON WINDOWS!
 *
 * @author lucifer
 *         Date 2016-10-16
 *         Device Aurora R5
 */
public class JavaVMStackOom {
    private void dontstop() {
        while (true) {

        }
    }

    private void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(() -> dontstop());
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOom oom = new JavaVMStackOom();
        oom.stackLeakByThread();
    }
}
