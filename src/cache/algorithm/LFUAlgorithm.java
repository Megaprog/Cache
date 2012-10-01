/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache.algorithm;

import cache.CacheAlgorithm;

/**
 * Implementation of Least Frequently Used (LFU) cache algorithm.
 * Sets number of applying as algorithm meter.
 */
public class LFUAlgorithm implements CacheAlgorithm {

    @Override
    public long calcMeter(long oldMeter) {
        return oldMeter + 1;
    }
}
