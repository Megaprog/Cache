/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache;

/**
 * Generates key of some object available in some {@link ObjectFactory}
 * @param <K> type of the key
 */
public interface KeyGenerator<K>  {

    /**
     * @return key of ome object available in some Factory.
     */
    K getKey();
}

