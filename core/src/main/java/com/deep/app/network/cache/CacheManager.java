package com.deep.app.network.cache;

import android.content.Context;
import com.deep.app.util.StringUtil;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class CacheManager {
    private static final String TAG = CacheManager.class.getSimpleName();
    private static final int MB = 1024 * 1024;
    private static final int DEFAULT_DISK_CACHE_SIZE = 50 * MB; //50MB
    private static CacheManager instance;
    private ICache<String> cache;

    private CacheManager() {
    }

    public static CacheManager getInstance() {
        if (null == instance) {
            instance = new CacheManager();
        }
        return instance;
    }

    public static String urlToKey(String url) {
        return StringUtil.md5Hex(url);
    }

    public void init(Context context, String uniqueName) {
        init(context, uniqueName, DEFAULT_DISK_CACHE_SIZE, CacheType.DISK);
    }

    public void init(Context context, String uniqueName, CacheType type) {
        init(context, uniqueName, DEFAULT_DISK_CACHE_SIZE, type);
    }

    public void init(Context context, String uniqueName, int cacheSize, CacheType type) {
        if (cacheSize <= 0) {
            cacheSize = DEFAULT_DISK_CACHE_SIZE;
        }
        switch (type) {
            case DISK:
                cache = new DiskLruCache(context, uniqueName, cacheSize);
                break;
            case MEMORY:
                cache = new MemoryLruCache(cacheSize);
            default:
                cache = new MemoryLruCache(cacheSize);
                break;
        }
    }

    public boolean saveData(String url, String response) {
        if (cache == null || instance == null) {
            throw new RuntimeException("Cache is not yet initialized.");
        }
        String key = urlToKey(url);
        String cacheResponse = cache.getCachedData(key);
        if (null != cacheResponse) {
            String netRes = urlToKey(response);
            String cacheRes = urlToKey(cacheResponse);
            if (cacheRes.equals(netRes)) {
                return false;
            }
        }
        cache.putCachedData(key, response);
        return true;
    }

    public Observable<String> getCachedData(final String url) {
        if (cache == null || instance == null) {
            throw new RuntimeException("Cache is not yet initialized.");
        }

        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> subscriber) throws Exception {
                try {
                    String key = urlToKey(url);
                    String response = cache.getCachedData(key);
                    if (response != null) {
                        subscriber.onNext(response);
                    }
                    subscriber.onComplete();
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
            }
        });
    }

    public enum CacheType {
        DISK, MEMORY
    }
}
