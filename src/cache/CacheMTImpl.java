/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache;

public class CacheMTImpl<K, V> extends CacheSTImpl<K, V> {

    public CacheMTImpl(CacheStore<K> store, CacheAlgorithm algorithm, ObjectSerializer<V> serializer, ObjectFactory<K, V> objectFactory,
                       int cacheMaxElements, int removeAtOnce) {
        super(store, algorithm, serializer, objectFactory, cacheMaxElements, removeAtOnce);
    }

    @Override
    public synchronized V getObject(K key) {
        return super.getObject(key);
    }

    @Override
    public synchronized void putObject(K key, V object) {
        super.putObject(key, object);
    }

    @Override
    public synchronized void removeObject(K key) {
        super.removeObject(key);
    }

    @Override
    public synchronized void clear() {
        super.clear();
    }
}
