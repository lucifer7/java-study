package design.EJ;

import java.util.Date;

/**
 * Usage: <b> Item 17 Design and Document for inheritance or else prohibit it </b>
 * Constructors must not invoke overridable methods
 *
 * @author lucifer
 *         Date 2017-1-23
 *         Device Aurora R5
 */
public class ConstructorOverride {
    public static void main(String[] args) {
        Sub sub = new Sub();    // This will cause final variable to have two states
        sub.overrideMe();
    }
}

class Super {
    Super(){
        overrideMe();
    }

    void overrideMe() {
    }
}

class Sub extends Super {
    private final Date date;

    Sub() {
        date = new Date();
    }

    @Override
    void overrideMe() {
        System.out.println(date);
    }
}
