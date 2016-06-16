package concurrency.thread.exception;

import lombok.extern.log4j.Log4j;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/16
 * Time: 14:47
 **/
@Log4j
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Exception occured, " + e.getMessage());
        log.info("Thread state: " + t.getState());
        log.error("Thread " + t.getName(), e);
    }
}
