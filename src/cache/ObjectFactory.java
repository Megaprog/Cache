/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache;

public interface ObjectFactory<K, V> {

    V getObject(K key);
}
