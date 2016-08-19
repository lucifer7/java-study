package data.type.generalization;

import lombok.extern.log4j.Log4j;

import java.util.Random;

/**
 * Usage: <b>泛型无法直接创建对象</b>
 * 解决方法：
 * 1. 传递工厂对象，通过其创建新实例
 * 2. 使用模板设计模式 <@see>GenericCreator</@see>
 *
 * @author lucifer
 * @date 2016-8-18
 * @devide Yoga Pro
 */
public class GenericNewInstance {
    public static void main(String[] args) {
        new Foo<Integer>(new IntegerFactory()).info();
        new Foo<Widget>(new Widget.FactoryInner()).info();
    }
}

class InvalidOPs<T> {
    private final int SIZE = 100;

    /* 对于泛型，任何在运行期需要知道确切类型的代码都不能工作 */
    public void f(Object arg) {
        /*if (arg instanceof T);      // class or array expected
        T var = new T();            // T cannot be instantiated directly
        T[] array = new T[];        // Array initializer expected*/
        T[] array0 = (T[]) new Object[SIZE];    // Unchecked cast

    }
}

interface Factory<T> {
    T create();
}

@Log4j
class Foo<T> {
    private T x;

    public <F extends Factory<T>> Foo(F factory) {
        x= factory.create();
    }

    public void info() {
        log.info(x.toString());
    }
}

class IntegerFactory implements Factory<Integer> {
    @Override
    public Integer create() {
        return new Random().nextInt();
    }
}

class Widget {
    public static class FactoryInner implements Factory<Widget> {
        @Override
        public Widget create() {
            return new Widget();
        }
    }
}