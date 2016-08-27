package design.pattern.concurrency.guardedSuspension;

/**
 * Usage: <b>Guarded Suspension 模式，即保护暂停</b>
 * 当服务器准备好时才提供服务
 * 用于平衡客户端请求速度与服务器处理能力的不对等情况
 * 队列可以缓存请求
 *
 * @author lucifer
 * @date 2016-8-27
 * @devide Yoga Pro
 */
public class SuspensionMain {
    public static void main(String[] args) {
        RequestQueue queue = new RequestQueue();

        for (int i = 0; i < 10; i++) {
            new ServerThread(queue, "Server_NO." + i).start();
        }

        for (int i = 0; i < 10; i++) {
            new ClientThread(queue, "Client_NO." + i).start();
        }
    }
}
