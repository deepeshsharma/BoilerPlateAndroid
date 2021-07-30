package com.deep.app.network.cache;

public interface ICache<T> {
    T getCachedData(String key);

    void putCachedData(String key, T value);
}
