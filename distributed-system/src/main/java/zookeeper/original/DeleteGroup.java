package zookeeper.original;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

import static zookeeper.original.common.ProjectProperties.ZOOKEEPER_HOST;
import static zookeeper.original.common.ProjectProperties.ZOOKEEPER_GROUP;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/8/30
 **/
@Slf4j
public class DeleteGroup extends ConnectionWatcher {
    // may also use ZKUtil.deleteRecursive ?
    public void delete(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        try {
            List<String> children = zk.getChildren(path, false);
            children.forEach(c -> {
                try {
                    zk.delete(path + "/" + c, -1);      // -1 to ignore version check
                } catch (Exception e) {
                    log.error("Remove znode failed", e);
                }
            });
            zk.delete(path, -1);
        } catch (KeeperException.NoNodeException e) {
            log.error("Group {} does not exist", groupName, e);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        DeleteGroup deleteGroup = new DeleteGroup();
        deleteGroup.connect(ZOOKEEPER_HOST);
        deleteGroup.delete(ZOOKEEPER_GROUP);
        deleteGroup.close();
    }
}
