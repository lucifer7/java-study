package zookeeper.original;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

import java.io.IOException;

import static zookeeper.original.common.ProjectProperties.ZOOKEEPER_HOST;
import static zookeeper.original.common.ProjectProperties.ZOOKEEPER_GROUP;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/8/30
 **/
@Slf4j
public class JoinGroup extends ConnectionWatcher {
    // make sure group is created beforehand
    public void join(String groupName, String memberName) throws KeeperException, InterruptedException {
        String path = "/" + groupName + "/" + memberName;
        String createdPath = zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        log.info("Creating path: {}", createdPath);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        JoinGroup joinGroup = new JoinGroup();
        joinGroup.connect(ZOOKEEPER_HOST);
        joinGroup.join(ZOOKEEPER_GROUP, "world");
        Thread.sleep(Long.MAX_VALUE);
    }
}
