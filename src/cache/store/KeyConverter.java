/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache.store;

public interface KeyConverter<K, T> {

    T keyTo(K key);

    K keyFrom(T some);
}
