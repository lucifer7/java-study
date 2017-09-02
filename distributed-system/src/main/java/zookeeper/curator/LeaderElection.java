package zookeeper.curator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.EnsurePath;

import java.util.concurrent.TimeUnit;

import static zookeeper.original.common.ProjectProperties.ZK_ADDRESS;
import static zookeeper.original.common.ProjectProperties.ZOOKEEPER_PATH;

/**
 * Usage: <b> Use LeaderSelector for leadership election </b>
 * Only one Listener will running takeLeadership() at the same time
 * and relinquish leadership when finish takeLeadership()
 *
 * @author Jingyi.Yang
 *         Date 2017/8/31
 **/
@Slf4j
public class LeaderElection {
    public static void main(String[] args) throws InterruptedException {
        LeaderSelectorListener listener = new LeaderSelectorListener() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                log.info("Leadership taken by {}", Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(5L);
                log.info("{} relinquish leadership", Thread.currentThread().getName());
            }

            @Override
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {

            }
        };

        new Thread(() -> registerListener(listener)).start();
        new Thread(() -> registerListener(listener)).start();
        new Thread(() -> registerListener(listener)).start();

        Thread.sleep(Long.MAX_VALUE);
    }

    private static void registerListener(LeaderSelectorListener listener) {
        CuratorFramework curator = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));
        curator.start();

        try {
            new EnsurePath(ZOOKEEPER_PATH).ensure(curator.getZookeeperClient());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LeaderSelector selector = new LeaderSelector(curator, ZOOKEEPER_PATH, listener);
        selector.autoRequeue();
        selector.start();
    }
}
