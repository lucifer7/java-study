package concurrency.ch4ThreadExecutor.threadRunner;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2016-9-26
 *         Device Aurora R5
 */
@Log4j
public class Server {
    private ThreadPoolExecutor executor;

    public Server() {
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    public void executeTask(Task task) {
        log.info("Server: a new task has arrived.");

        executor.execute(task);

        log.info("Server: poll size: " + executor.getPoolSize());
        log.info("Server: active count: " + executor.getActiveCount());
        log.info("Server: completed tasks: " + executor.getCompletedTaskCount());
    }

    public void endServer() {
        executor.shutdown();
        log.info("Server: shutdown slowly");
    }

    public void shutdownNow() {
        executor.shutdownNow();
        log.info("Server: shutdown quickly");
        log.info("Server: state: " + executor.isShutdown());
    }
}
