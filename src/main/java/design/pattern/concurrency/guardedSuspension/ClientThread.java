package design.pattern.concurrency.guardedSuspension;

import com.google.common.collect.Lists;
import design.pattern.concurrency.future.Data;
import design.pattern.concurrency.future.FutureData;
import lombok.extern.log4j.Log4j;

import java.util.List;

/**
 * Usage: <b>客户端进程</b>
 *
 * @author lucifer
 * @date 2016-8-27
 * @device Yoga Pro
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
        //List<Data> data = Lists.newArrayList();
        List<Request> requests = Lists.newArrayList();

        for (int i = 0; i < 10; i++) {
            Request request = new Request(String.format("Request ID: %d, thread name: %s.", i, Thread.currentThread().getName()));
            log.info(Thread.currentThread().getName() + " requests " + request);

            FutureData futureData = new FutureData();       // 设置数据请求
            request.setResponse(futureData);

            queue.setRequest(request);
            requests.add(request);

            try {
                Thread.sleep(10);           // Client requests faster than server
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Data dataRes = request.getResponse();
            //data.add(dataRes);

            log.info("Client thread is: " + Thread.currentThread().getName());
        }

        log.info(Thread.currentThread().getName() + " Client finishes request.");

        for (Request re :
                requests) {
            log.info("Response for " + re.getName() + " is: " + re.getResponse().getResult());
        }

        /*for (Data d :
                data) {
            while (d.getResult() == null) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("---Response is: " + d.getResult());
        }*/
        //}
    }
}
