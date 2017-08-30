package zookeeper.original;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

import static zookeeper.original.common.ProjectProperties.ZOOKEEPER_HOST;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/8/30
 **/
@Slf4j
public class ListGroup extends ConnectionWatcher {
    public void list(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;

        try {
            List<String> children = zk.getChildren(path, false);
            if (children.isEmpty()) {
                log.info("No members in group {}", groupName);
                return;
            }

            children.forEach(log::info);
        } catch (KeeperException.NoNodeException e) {
            log.error("Group {} does not exist", groupName, e);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ListGroup listGroup = new ListGroup();
        listGroup.connect(ZOOKEEPER_HOST);
        listGroup.list("zookeeper");
        listGroup.close();
    }
}
