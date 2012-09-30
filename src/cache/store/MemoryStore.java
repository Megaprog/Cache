/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache.store;

import cache.CacheStore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryStore<K> implements CacheStore<K> {

    protected Map<K, byte[]> data = createMap();

    @Override
    public byte[] load(K key) {
        return data.get(key);
    }

    @Override
    public void save(K key, byte[] buffer) {
        data.put(key, buffer);
    }

    @Override
    public int length() {
        return data.size();
    }

    @Override
    public Collection<K> getKeys() {
        return data.keySet();
    }

    @Override
    public boolean contains(K key) {
        return data.containsKey(key);
    }

    @Override
    public void remove(K key) {
        data.remove(key);
    }

    @Override
    public void clear() {
        data.clear();
    }

    protected Map<K, byte[]> createMap() {
        return new HashMap<>();
    }
}
