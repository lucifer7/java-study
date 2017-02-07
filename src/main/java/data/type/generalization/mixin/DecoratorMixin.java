package data.type.generalization.mixin;

import lombok.extern.log4j.Log4j;

import java.util.Date;

/**
 * Usage: <b> 使用装饰器模式产生混型 </b>
 * 缺点：即使可以包装很多层，但只有最后一层是实际类型
 *
 * @author lucifer
 *         Date 2017-2-6
 *         Device Aurora R5
 */
@Log4j
public class DecoratorMixin {
    public static void main(String[] args) {
        Timestamped0 t = new Timestamped0(new Basic0());
        Timestamped0 t1 = new Timestamped0(new SerialNumbered(new Basic0()));
        log.info(t.getTimestamp());
        log.info(t1.getTimestamp());
        // t1.getSerialNumber();    // not supported

        SerialNumbered s = new SerialNumbered(new Basic0());
        SerialNumbered s1 = new SerialNumbered(new Timestamped0(new Basic0()));
        log.info(s.getSerialNumber());
        log.info(s1.getSerialNumber());
        // s1.getTimestamp();       // not supported
    }
}

class Basic0 {
    private String value;

    void setValue(String value) {
        this.value = value;
    }
    String getValue() {
        return value;
    }
}

class Decorator extends Basic0 {
    Basic0 basic0;

    Decorator(Basic0 basic0) {
        this.basic0 = basic0;
    }

    @Override
    void setValue(String value) {
        basic0.setValue(value);
    }

    @Override
    String getValue() {
        return basic0.getValue();
    }
}

class Timestamped0 extends Decorator {
    private final long timestamp;

    Timestamped0(Basic0 basic0) {
        super(basic0);
        timestamp = new Date().getTime();
    }

    long getTimestamp() {
        return timestamp;
    }
}

class SerialNumbered extends Decorator {
    private static long counter = 1;
    private final long serialNumber = counter++;

    SerialNumbered(Basic0 basic0) {
        super(basic0);
    }

    long getSerialNumber() {
        return serialNumber;
    }
}
