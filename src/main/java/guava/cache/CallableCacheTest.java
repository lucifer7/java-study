package guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/2
 * Time: 9:46
 **/
@Log4j
public class CallableCacheTest {
    Cache<String, String> cache;

    @Before
    public void initCacheBuilder() {
        cache = CacheBuilder
                .newBuilder()
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .initialCapacity(10)
                .expireAfterWrite(1000L, TimeUnit.SECONDS)
                .softValues()
                .build();
    }

    @Test
    public void cacheAccessTest() {
        try {
            String ret = cache.get("keys222", new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return "keys222" + " callable generated. ";
                }
            });

            log.info(ret);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
