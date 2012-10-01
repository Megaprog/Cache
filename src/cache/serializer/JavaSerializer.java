/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache.serializer;

import cache.ObjectSerializer;

import java.io.*;

/**
 * Implementation of {@link ObjectSerializer} which uses standard Java serialization.
 * @param <T> type of serialized object.
 */
public class JavaSerializer<T> implements ObjectSerializer<T> {

    public void serializeObject(T object, OutputStream os) {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(object);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public T deSerializeObject(InputStream is) {
        T res = null;
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            res = (T) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }

}
