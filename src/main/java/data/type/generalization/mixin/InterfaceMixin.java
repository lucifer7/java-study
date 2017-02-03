package data.type.generalization.mixin;

import lombok.extern.log4j.Log4j;

import java.util.Date;

/**
 * Usage: <b> 使用接口产生混型效果 </b>
 * Basically you are using proxy
 *
 * @author lucifer
 *         Date 2017-2-3
 *         Device Aurora R5
 */
@Log4j
public class InterfaceMixin {
    public static void main(String[] args) {
        Mixin mixin1 = new Mixin(), mixin2 = new Mixin();
        mixin1.set("Mixin first.");
        mixin2.set("Mixin second.");
        log.info(mixin1.get() + " " + mixin1.getStamp() + " " + mixin1.getSerialNum());
        log.info(mixin2.get() + " " + mixin2.getStamp() + " " + mixin2.getSerialNum());
    }
}

interface Timestamped { long getStamp(); }

class TimestampedImpl implements Timestamped {
    private final long timestamp;

    public TimestampedImpl() {
        timestamp = new Date().getTime();
    }

    @Override
    public long getStamp() {
        return timestamp;
    }
}

interface SerialNum { long getSerialNum(); }

class SerialNumImpl implements SerialNum {
    private static long counter = 1;
    private final long serialNumber = counter++;

    @Override
    public long getSerialNum() {
        return serialNumber;
    }
}

interface Basic {
    void set(String val);
    String get();
}

class BasicImpl implements Basic {
    private String value;

    @Override
    public void set(String val) {
        value = val;
    }

    @Override
    public String get() {
        return value;
    }
}

class Mixin extends BasicImpl implements Timestamped, SerialNum {
    private Timestamped timestamped = new TimestampedImpl();
    private SerialNum serialNum = new SerialNumImpl();

    @Override
    public long getStamp() {
        return timestamped.getStamp();
    }

    @Override
    public long getSerialNum() {
        return serialNum.getSerialNum();
    }
}