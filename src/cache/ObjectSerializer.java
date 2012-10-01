/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Save object to {@link OutputStream} and load object from {@link InputStream}
 * @param <V> type of the object
 */
public interface ObjectSerializer<V> {

    void serializeObject(V object, OutputStream os);

    V deSerializeObject(InputStream is);
}
