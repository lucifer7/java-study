package concurrency.ch4ThreadExecutor.threadRunner;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2016-9-26
 *         Device Aurora R5
 */
public class RunnerMain {
    public static void main(String[] args) {
        Server server = new Server();

        for (int i = 0; i < 100; i++) {
            Task task = new Task("task-" + i);
            server.executeTask(task);
        }

        //server.endServer();
        server.shutdownNow();
    }
}
