package design.pattern.concurrency.masterWorker;

import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 * @date 2016-8-24
 * @devide Yoga Pro
 */
@Log4j
public class ExecuteMain {
    public static void main(String[] args) {
        Master master = new Master(new MultiWorker(), 4);

        for (int i = 0; i < 10000; i++) {
            master.submit(new Random().nextInt(100));
        }

        master.start();

        Map<String, Object> resultMap = master.getResultMap();
        /*for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
            log.info(String.format("Result for %s is %d.", entry.getKey(), entry.getValue()));
        }*/

        BigDecimal result = BigDecimal.valueOf(0L);
        while (MapUtils.isNotEmpty(resultMap) || !master.isComplete()) {
            for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
                if (entry.getKey() == null) break;
                result = result.add(new BigDecimal((Integer) entry.getValue()));
                resultMap.remove(entry.getKey());
            }
        }

        log.info("Total result is: " + result);
    }
}
