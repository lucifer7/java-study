package zookeeper.original;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/8/29
 **/
@Slf4j
public class ConnectionWatcher implements Watcher {
    private static final int SESSION_TIMEOUT = 5000;
    protected ZooKeeper zk;
    private CountDownLatch connectedSignal = new CountDownLatch(1);

    public void connect(String hosts) throws IOException, InterruptedException {
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
        connectedSignal.await();                // blocked for connection success signal
        log.info("Connect to Zookeeper success.");
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            connectedSignal.countDown();        // release block when connection success
        }
    }

    public void close() throws InterruptedException {
        zk.close();
    }
}
