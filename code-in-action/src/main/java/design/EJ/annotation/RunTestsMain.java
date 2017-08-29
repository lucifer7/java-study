package design.EJ.annotation;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2017-3-5
 *         Device Aurora R5
 */
@Slf4j
public class RunTestsMain {
    public static void main(String[] args) throws ClassNotFoundException {
        int tests = 0;
        int passed =0;

        Class testClass = Class.forName("design.EJ.annotation.Sample");
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                tests++;

                try {
                    m.invoke(null);
                    passed++;
                } catch (IllegalAccessException e) {
                    log.error("Illegal access: {}.", m);
                } catch (InvocationTargetException e) {
                    log.error("{} failed {}.", m, e.getCause());
                } catch (Exception e) {
                    log.error("Invalid @Test: {}.", m);
                }
            }
        }
        log.info("Tests passed {}, failed {}.", passed, tests - passed);
    }
}
