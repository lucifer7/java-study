package data.type.generalization;

import lombok.extern.log4j.Log4j;

/**
 * Usage: 泛型擦除的负面影响, 合适的泛型使用
 * Created by Lucifer
 * Date: 2016-8-15
 * Device: Aurora R5
 */
public class EraseDefect {
    public static void main(String[] args) {
        HasF hasf = new HasF();
        Manipulator<HasF> hasFManipulator = new Manipulator<>(hasf);
        hasFManipulator.manipulate();

        ManipulatorAdjusted<HasF> manipulatorAdjusted = new ManipulatorAdjusted<>(hasf);
        manipulatorAdjusted.manipulate();
    }
}

@Log4j
class HasF {
    public void f() {
        log.info("HasF.f() is running.");
    }
}

/* 不正解的泛型使用 */
class Manipulator<T> {
    private T obj;

    public Manipulator(T obj) {
        this.obj = obj;
    }

    public void manipulate() {
        /* Invalid method invoke */
        //obj.f();    //找不到符号
    }
}

/* 修正后的泛型使用，给泛型一个边界 */
class ManipulatorAdjusted<T extends HasF> {
    private T obj;

    public ManipulatorAdjusted(T obj) {
        this.obj = obj;
    }

    public void manipulate() {
        obj.f();
    }

    public T produce() {
        return obj;
    }
}
