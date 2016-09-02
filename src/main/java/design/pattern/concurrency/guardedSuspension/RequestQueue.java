package design.pattern.concurrency.guardedSuspension;

import com.google.common.collect.Lists;

import java.util.LinkedList;

/**
 * Usage: <b>客户端请求队列，同步加锁</b>
 *
 * @author lucifer
 * @date 2016-8-27
 * @device Yoga Pro
 */
public class RequestQueue {
    private LinkedList queue = Lists.newLinkedList();

    public synchronized Request getRequest() {
        while (queue.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }           // wait till new request added
        //return (Request) queue.getFirst();
        return (Request) queue.remove();
    }

    public synchronized void setRequest(Request request) {
        queue.add(request);
        notifyAll();
    }
}
