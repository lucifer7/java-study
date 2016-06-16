package concurrency.thread.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/16
 * Time: 14:56
 *
 *  run() 方法不接受 throws 子句。当一个非检查异常被抛出，默认的行为是在控制台写下stack trace并退出程序。
 *  now we can use UncaughtExceptionHandler to handle unchecked exception
 **/
public class ErrorCaughtMain {
    public static void main(String[] args) {
        Thread thread = new Thread(new ErrorTask());
        thread.setUncaughtExceptionHandler(new ExceptionHandler());
        thread.start();
    }
}
