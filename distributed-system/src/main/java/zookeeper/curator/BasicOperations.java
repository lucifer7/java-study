package zookeeper.curator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.RetryOneTime;

import static zookeeper.original.common.ProjectProperties.*;

/**
 * Usage: <b> Connection, watcher(listener) using Curator framework </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/8/30
 **/
@Slf4j
public class BasicOperations {
    private static final String ZK_ADDRESS = ZOOKEEPER_HOST + ":" + ZOOKEEPER_PORT;

    public static void main(String[] args) throws Exception {
        // 1 Start connection
        CuratorFramework curator = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryOneTime(1000));
        curator.start();
        log.info("ZK cli start");

        // 2 Register listener
        // Listening on child(add, del, update), not on self on grandchildren ?
        PathChildrenCache watcher = new PathChildrenCache(curator, ZOOKEEPER_PATH, true);
        watcher.getListenable().addListener((curatorFramework, event) -> {
            ChildData data = event.getData();
            if (null == data) {
                log.info("No data in event[{}]", event);
            } else {
                log.info("Receiving event: type=[{}], path=[{}], data=[{}], stat=[{}]",
                        event.getType(), new Object[]{data.getPath(), new String(data.getData()), data.getStat()});
            }
        });

        watcher.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        log.info("Register Zk watcher");

        Thread.sleep(Long.MAX_VALUE);
    }
}
