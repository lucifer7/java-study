package design.pattern.concurrency.guardedSuspension;

import design.pattern.concurrency.future.FutureData;
import design.pattern.concurrency.future.RealData;
import lombok.extern.log4j.Log4j;

/**
 * Usage: <b>服务器进程</b>
 *
 * @author lucifer
 * @date 2016-8-27
 * @devide Yoga Pro
 */
@Log4j
public class ServerThread extends Thread{
    private RequestQueue queue;

    public ServerThread(RequestQueue queue, String name) {
        super(name);
        this.queue = queue;
    }

    public void run() {
        while (true) {
            final Request request = queue.getRequest();

            FutureData data = (FutureData) request.getResponse();                   // 获取 Client 的 FutureData
            data.setRealData(new RealData("Real data from " + request.getName()));  // 返回真实的数据：处理完成后，通知 Client

            //request.setResponse(data);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(String.format("%s handles %s.", Thread.currentThread().getName(), request));
        }
    }
}
