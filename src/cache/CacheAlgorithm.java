/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache;

/**
 * Algorithm for {@link Cache}.
 * Gets old meter and return a new one
 */
public interface CacheAlgorithm {

    /**
     * Gets old meter and return a new one
     * @param oldMeter previous meter
     * @return new meter after applying algorithm to old one
     */
    long calcMeter(long oldMeter);
}
