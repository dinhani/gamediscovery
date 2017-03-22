package gd.infrastructure.cache;

public class GuavaCacheMXBeanImpl implements GuavaCacheMXBean {

    private final com.google.common.cache.Cache _cache;

    public GuavaCacheMXBeanImpl(com.google.common.cache.Cache cache) {
        _cache = cache;
    }

    @Override
    public long getRequestCount() {
        return _cache.stats().requestCount();
    }

    @Override
    public long getHitCount() {
        return _cache.stats().hitCount();
    }

    @Override
    public double getHitRate() {
        return _cache.stats().hitRate();
    }

    @Override
    public long getMissCount() {
        return _cache.stats().missCount();
    }

    @Override
    public double getMissRate() {
        return _cache.stats().missRate();
    }

    @Override
    public long getLoadCount() {
        return _cache.stats().loadCount();
    }

    @Override
    public long getLoadSuccessCount() {
        return _cache.stats().loadSuccessCount();
    }

    @Override
    public long getLoadExceptionCount() {
        return _cache.stats().loadExceptionCount();
    }

    @Override
    public double getLoadExceptionRate() {
        return _cache.stats().loadExceptionRate();
    }

    @Override
    public long getTotalLoadTime() {
        return _cache.stats().totalLoadTime();
    }

    @Override
    public double getAverageLoadPenalty() {
        return _cache.stats().averageLoadPenalty();
    }

    @Override
    public long getEvictionCount() {
        return _cache.stats().evictionCount();
    }

    @Override
    public long getSize() {
        return _cache.size();
    }

    @Override
    public void cleanUp() {
        _cache.cleanUp();
    }

    @Override
    public void invalidateAll() {
        _cache.invalidateAll();
    }
}
