/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache;

import java.util.Collection;

public interface CacheStore<K> {

    byte[] load(K key);

    void save(K key, byte[] buffer);

    int length();

    Collection<K> getKeys();

    boolean contains(K key);

    void remove(K key);

    void clear();
}
