package design.pattern.concurrency.guardedSuspension;

import lombok.extern.log4j.Log4j;

/**
 * Usage: <b>客户端进程</b>
 *
 * @author lucifer
 * @date 2016-8-27
 * @devide Yoga Pro
 */
@Log4j
public class ClientThread extends Thread {
    private RequestQueue queue;

    public ClientThread(RequestQueue queue, String name) {
        super(name);
        this.queue = queue;
    }

    public void run() {
        //while (true) {
            for (int i = 0; i < 10; i++) {
                Request request = new Request(String.format("Request ID: %d, thread name: %s.", i, Thread.currentThread().getName()));
                log.info(Thread.currentThread().getName() + " requests " + request);

                queue.setRequest(request);

                try {
                    Thread.sleep(10);           // Client requests faster than server
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                log.info("Client thread is: " + Thread.currentThread().getName());
            }

            log.info(Thread.currentThread().getName() + " Client finishes request.");
        //}
    }
}
