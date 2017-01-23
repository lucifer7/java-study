package data.type.generalization.selfBounded;

/**
 * Usage: <b> 自限定将强制泛型当作其自己的边界参数来使用，可以保证类型参数与正在被定义的类相同 </b>
 * F可以编译，自限定惯用法不是可强制执行的
 * 移除自限定所有的类都可以编译，包括
 *
 * @author lucifer
 *         Date 2017-1-23
 *         Device Aurora R5
 */
public class SelfBounding {
    public static void main(String[] args) {
        A a = new A();
        a.set(new A());
        a = a.set(new A()).get();
        a = a.get();

        C c = new C();
        c = c.setAndGet(new C());
    }
}

class SelfBounded<T extends SelfBounded<T>> {
    T element;

    SelfBounded<T> set(T arg) {
        element = arg;
        return this;
    }

    T get() {
        return element;
    }
}

class A extends SelfBounded<A> {}

class B extends SelfBounded<A> {}

class C extends SelfBounded<C> {
    C setAndGet(C arg) {
        set(arg);
        return get();
    }
}

class D {}

/* Compile error: type parameter D is not within its bound, should extend SelfBounded*/
// class E extends SelfBounded<D> {}

class F extends SelfBounded {}
