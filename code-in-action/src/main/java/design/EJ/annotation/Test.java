package design.EJ.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Usage: <b> Indicates that annotated class is a test method </b>
 * And use only on static method with no parameters
 *
 * @author lucifer
 *         Date 2017-3-5
 *         Device Aurora R5
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Test {
}
