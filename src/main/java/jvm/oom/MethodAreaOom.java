package jvm.oom;

/*import org.mockito.cglib.proxy.Enhancer;
import org.mockito.cglib.proxy.MethodInterceptor;*/

/* Mind mockito version */

/**
 * Usage: <b> </b>
 * VM args: -XX:PermSize=10M -XX:MaxPermSize=10M
 *
 * @author lucifer
 *         Date 2016-10-16
 *         Device Aurora R5
 */
public class MethodAreaOom {
    /*public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (obj, method, objects, proxy) -> proxy.invokeSuper(obj, objects));
            enhancer.create();
        }
    }*/

    static class OOMObject {

    }
}
