/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache.store;

/**
 * Convert key to/from some other type
 * @param <K> type of the key
 * @param <T> type to/from key will be converted
 */
public interface KeyConverter<K, T> {

    T keyTo(K key);

    K keyFrom(T some);
}
