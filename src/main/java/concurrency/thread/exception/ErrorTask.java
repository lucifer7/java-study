package concurrency.thread.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/16
 * Time: 14:54
 **/
public class ErrorTask implements Runnable {
    @Override
    public void run() {
        Integer num = Integer.parseInt("doubi");
    }
}
