/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache;

import java.io.InputStream;
import java.io.OutputStream;

public interface ObjectSerializer<V> {

    void serializeObject(V object, OutputStream os);

    V deSerializeObject(InputStream is);
}
