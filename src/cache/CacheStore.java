/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache;

import java.util.Collection;

/**
 * Store byte arrays by specified unique key.
 * @param <K> type of the key of stored byte array
 */
public interface CacheStore<K> {

    /**
     * Load byte array from the store by specified key
     * @param key unique key
     * @return byte array corresponded to the key
     */
    byte[] load(K key);

    /**
     * Saves byte array to the store by specified key
     * @param key unique key
     * @param buffer some byte array
     */
    void save(K key, byte[] buffer);

    /**
     * @return number of stored byte arrays
     */
    int length();

    /**
     * @return the collection of keys of byte arrays saved to the store
     */
    Collection<K> getKeys();

    /**
     * Checks if store contains byte array with specified key.
     * @param key unique key
     * @return true if contains false otherwise
     */
    boolean contains(K key);

    /**
     * Removes byte array with specified key from the store
     * @param key unique key
     */
    void remove(K key);

    /**
     * Removes all data from store.
     */
    void clear();
}
