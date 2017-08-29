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
public class ExceptionTestsMain {
    public static void main(String[] args) throws ClassNotFoundException {
        int tests = 0;
        int passed = 0;

        Class testClass = Class.forName("design.EJ.annotation.Sample2");
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(ExceptionTest.class)) {
                tests++;

                try {
                    m.invoke(null);
                } catch (IllegalAccessException e) {
                    log.error("Illegal access: {}.", m);
                } catch (InvocationTargetException e) {
                    Throwable exc = e.getCause();
                    Class<? extends Exception> excType = m.getAnnotation(ExceptionTest.class).value();
                    if (excType.isInstance(exc)) {
                        passed++;
                    } else {
                        log.error("Test {} failed, expected {}, get {}.", m, excType.getName(), e.getCause());
                    }
                } catch (Exception e) {
                    log.error("Invalid @Test: {}.", m);
                }
            }
        }
        log.info("Tests passed {}, failed {}.", passed, tests - passed);
    }
}
