package guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/1
 * Time: 16:55
 **/
@Log4j
public class LoadingCacheTest {
    LoadingCache<String, String> cache;

    @Before
    public void initCache() {
        cache =
                CacheBuilder.newBuilder()
                        .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                        .expireAfterWrite(2L, TimeUnit.SECONDS)
                        .initialCapacity(2)
                        .maximumSize(4)
                        .weakValues()
                        .build(new CacheLoader<String, String>() {

                            @Override
                            public String load(String key) throws Exception {
                                return key + " loaded. ";
                            }
                        });
        //loader is executed when using apply() OR get key while absent

        cache.put("keys123", "value123");
        cache.put("keys123", "value1245");
        cache.put("keys124", "value124");
        cache.put("keys125", "value125");
    }

    @Test
    public void lrcTest() {
        cache.put("keys126", "value126");
        try {
            cache.get("keys123");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        cache.put("keys127", "value127");   //over limits, least recently used item will be expelled
        cacheIteratorTest();
        try {
            cache.apply("keys128");
            log.info(cache.get("keys128"));
            log.info(cache.get("keys129"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void softRefTest() {
        cacheIteratorTest();
        System.gc();
        log.info("----------GCC Executed----------");
        cacheIteratorTest();
    }

    //@Test
    public void cacheIteratorTest() {
        ConcurrentMap<String, String> map = cache.asMap();
        for (Map.Entry<String, String> item: map.entrySet()) {
            log.info(item);
        }
    }

    //@Test
    public void cacheExpireTest() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //cache.invalidate("keys125");
        log.info("-----------CACHE EXPIRES----------");
        log.info(cache.getIfPresent("keys125"));
    }

    //@Test
    public void cacheGetTest() {
        try {
            log.info(cache.get("keys123"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
