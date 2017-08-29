package concurrency.ch3SyncTools.phaser;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Usage: <b> 文件搜索类，搜索确定的扩展名且24小时更改的文件列表 </b>
 *
 * @author Jingyi.Yang
 * Date 2016/9/14
 **/
@Log4j
public class FileSearch implements Runnable {
    private String initPath;
    private String extend;
    private List<String> results;
    private Phaser phaser;

    public FileSearch(String initPath, String extend, Phaser phaser) {
        this.initPath = initPath;
        this.extend = extend;
        this.phaser = phaser;
        this.results = Lists.newArrayList();
    }

    /* 递归搜索目录，处理找到的文件 */
    private void directoryProcess(File file) {
        File[] list = file.listFiles();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    directoryProcess(list[i]);
                } else {
                    fileProcess(list[i]);
                }
            }
        }
    }

    /* 处理文件，选出扩展名相同的文件 */
    private void fileProcess(File file) {
        if (file.getName().endsWith(extend)) {
            results.add(file.getAbsolutePath());
        }
    }

    /* 筛选结果：24小时内修改过 */
    private void filterResults() {
        List<String> newResults = Lists.newArrayListWithCapacity(results.size());
        long actualDate = new Date().getTime();

        Iterator<String> iterator = results.iterator();
        for (; iterator.hasNext(); ) {
            File file = new File(iterator.next());
            long fileDate = file.lastModified();

            if (actualDate - fileDate > TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)) {
                iterator.remove();
            }
        }
    }

    /* 检查结果，是否为空 */
    private boolean checkResults() {
        log.info(String.format("%s: Phase %s: %d results.", Thread.currentThread().getName(), phaser.getPhase(), results.size()));
        if (results.isEmpty()) {
            phaser.arriveAndDeregister();
            return false;
        } else {
            phaser.arriveAndAwaitAdvance();
            return true;
        }
    }

    private void showInfo() {
        for (String result : results) {
            log.info(String.format("%s: %s", Thread.currentThread().getName(), result));
        }
        phaser.arriveAndAwaitAdvance();
    }

    @Override
    public void run() {
        log.info(String.format("%s: Starting.", Thread.currentThread().getName()));
        phaser.arriveAndAwaitAdvance();

        File file = new File(initPath);
        if (file.isDirectory()) {
            directoryProcess(file);
        } else {
            fileProcess(file);
        }

        filterResults();

        if (!checkResults()) {
            return;
        }

        showInfo();
        phaser.arriveAndAwaitAdvance();
        log.info(String.format("%s: Work Completed.", Thread.currentThread().getName()));

    }
}
