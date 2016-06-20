package concurrency.thread.group.exception;

import lombok.extern.log4j.Log4j;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/20
 * Time: 16:49
 **/
@Log4j
public class ExThreadGroup extends ThreadGroup {
    private String name;

    public ExThreadGroup(String name) {
        super(name);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }

}
