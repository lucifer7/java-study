package guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.log4j.Log4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/2
 * Time: 10:06
 **/
@Log4j
public class GeneralizedCacheTest {
    private <K, V>LoadingCache<K, V> _getCache(CacheLoader<K, V> cacheLoader) {
        LoadingCache<K, V> cache = CacheBuilder.newBuilder()
                .initialCapacity(4)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .expireAfterWrite(1L, TimeUnit.DAYS)
                .build(cacheLoader);
        return cache;
    }

    private LoadingCache<String, String> stringCache() {
        LoadingCache<String, String> cache = _getCache(new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                return key + " loaded. ";
            }
        });
        return cache;
    }

    @Test
    public void testCache() {
        LoadingCache<String, String> cache = stringCache();
        try {
            log.info(cache.get("new key"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
