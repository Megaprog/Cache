/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache.algorithm;

import cache.CacheAlgorithm;

/**
 * Implementation of Least Recently Used (LRU) cache algorithm.
 * Sets current time as algorithm meter.
 */
public class LRUAlgorithm implements CacheAlgorithm {

    @Override
    public long calcMeter(long oldMeter) {
        return System.currentTimeMillis();
    }
}
