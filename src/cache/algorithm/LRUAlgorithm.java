/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache.algorithm;

import cache.CacheAlgorithm;

public class LRUAlgorithm implements CacheAlgorithm {

    @Override
    public long calcMeter(long oldMeter) {
        return System.currentTimeMillis();
    }
}
