/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache;

import java.io.Serializable;

public interface Cache<K, V> extends ObjectFactory<K, V> {

    void putObject(K key, V object);

    void removeObject(K key);

    void clear();
}
