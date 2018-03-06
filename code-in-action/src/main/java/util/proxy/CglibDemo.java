package util.proxy;

import lombok.extern.log4j.Log4j;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.Mixin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Usage: <b> Dynamic code generation as proxy </b>
 * Implement class and interface at runtime
 *
 * @author Jingyi.Yang
 *         Date 2018/3/6
 **/
@Log4j
public class CglibDemo {
    public static void main(String[] args) {
        fixedReturn();

        surroundingMethod();

        try {
            generateBean();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("Generate bean failed", e);
        }

        createMixin();
    }

    /**
     * Return fixed value for proxied method, despite input
     */
    private static void fixedReturn() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ServiceA.class);

        enhancer.setCallback((FixedValue) () -> 233);
        ServiceA proxy = (ServiceA) enhancer.create();
        Integer length = proxy.length("word");
        log.info(length);
    }

    /**
     * Do some job surround the proxied method
     */
    private static void surroundingMethod() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ServiceA.class);

        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            log.info("<interceptor working>");
            return methodProxy.invokeSuper(o, objects);
        });

        ServiceA proxy = (ServiceA) enhancer.create();
        String upper = proxy.toUpper("word");
        log.info(upper);
    }

    /**
     * Generate bean with properties and methods
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static void generateBean() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BeanGenerator beanGenerator = new BeanGenerator();
        beanGenerator.addProperty("name", String.class);
        Object bean = beanGenerator.create();
        Method setter = bean.getClass().getMethod("setName", String.class);
        setter.invoke(bean, "cglib-bean");

        Method getter = bean.getClass().getMethod("getName");
        Object res = getter.invoke(bean);
        log.info(res);
        log.info(bean.getClass());
    }

    /**
     * Create mixin of concrete type(requires mixed interface)
     */
    private static void createMixin() {
        Mixin mixin = Mixin.create(
                new Class[]{InterfaceA.class, InterfaceB.class, MixinInterface.class},
                new Object[]{new ClassA(), new ClassB()});
        MixinInterface mixinDelegate = (MixinInterface) mixin;

        String methodA = mixinDelegate.methodA();
        String methodB = mixinDelegate.methodB();
        log.info(methodA);
        log.info(methodB);
    }

    interface InterfaceA {
        String methodA();
    }

    interface InterfaceB {
        String methodB();
    }

    static class ClassA implements InterfaceA {

        @Override
        public String methodA() {
            return "class A running method A";
        }
    }

    static class ClassB implements InterfaceB {

        @Override
        public String methodB() {
            return "class B running method B";
        }
    }

    interface MixinInterface extends InterfaceA, InterfaceB {}
}
