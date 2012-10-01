/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache;

/**
 * Interface of some very abstract Cache which allow to get and put some object by unique key.
 * @param <K> type of the key
 * @param <V> type of the value
 */
public interface Cache<K, V> extends ObjectFactory<K, V> {

    /**
     * Store object in cache or replays old value by new if object with specified key already exists.
     * @param key object unique key
     * @param object object to store
     */
    void putObject(K key, V object);

    /**
     * Remove object with specified key.
     * @param key object unique key
     */
    void removeObject(K key);

    /**
     * Remove all objects in cache.
     */
    void clear();
}
