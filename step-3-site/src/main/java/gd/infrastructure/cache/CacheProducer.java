package gd.infrastructure.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDProducer;
import java.util.Map;
import org.slf4j.Logger;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

@GDProducer
@EnableCaching(proxyTargetClass = true)
public class CacheProducer {

    // CACHE NAMES
    public static final String FEATURED = "cache-featured";

    // DATA
    private SimpleCacheManager simpleCacheManager;
    private final Map<String, GuavaCacheMXBeanImpl> jmxWrappers = Maps.newHashMap();

    private static final Logger LOGGER = LogProducer.getLogger(CacheProducer.class);

    // =========================================================================
    // BEANS
    // =========================================================================
    @Bean
    public CacheManager cacheManager() {
        if (simpleCacheManager == null) {
            initSimpleCacheManager();
        }
        return simpleCacheManager;
    }

    private void initSimpleCacheManager() {
        LOGGER.info(LogMarker.INIT, "Initializing SimpleCacheManager");
        simpleCacheManager = new SimpleCacheManager();

        // create caches
        GuavaCache featuredQueriesCache = new GuavaCache(FEATURED, CacheBuilder.newBuilder()
                .recordStats().maximumSize(16)
                .build());
        jmxWrappers.put(FEATURED, new GuavaCacheMXBeanImpl(featuredQueriesCache.getNativeCache()));

        // create manager
        simpleCacheManager.setCaches(ImmutableList.of(featuredQueriesCache));
    }

    // =========================================================================
    // GETTERS
    // =========================================================================
    public Map<String, GuavaCacheMXBeanImpl> getStatistics() {
        return jmxWrappers;
    }

}
