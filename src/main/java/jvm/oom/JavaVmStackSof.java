package jvm.oom;

import lombok.extern.log4j.Log4j;

/**
 * Usage: <b> Test vm stack StackOverFlowError </b>
 * VM args: -Xss128k
 *
 * @author lucifer
 *         Date 2016-10-9
 *         Device Aurora R5
 */
@Log4j
public class JavaVmStackSof {
    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVmStackSof sof = new JavaVmStackSof();
        try {
            sof.stackLeak();
        } catch (Throwable e) {     // MIND!! Catch Throwable, not Exception, because Error extends Throwable
            log.error("stack length: " + sof.stackLength);
            throw e;
        }
    }
}
