package com.deep.app.network.cache;

import android.util.LruCache;

public class MemoryLruCache extends LruCache<String, String> implements ICache<String> {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public MemoryLruCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, String value) {
        return value.length();
    }

    @Override
    public String getCachedData(String key) {
        return get(key);
    }

    @Override
    public void putCachedData(String key, String value) {
        put(key, value);
    }
}
