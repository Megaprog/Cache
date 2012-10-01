/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache.store;

import cache.CacheStore;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Implements {@link CacheStore} by storing byte arrays in file system.
 * Used {@link KeyConverter} to convert key to/from String and makes file name from it string.
 * @param <K> type of the key
 */
public class FileStore<K> implements CacheStore<K> {

    protected Path dir;
    protected Set<K> keys = createSet();
    private KeyConverter<K, String> converter;

    /**
     * Creates new instance of {@link FileStore}.
     * @param workingDir name of the directory where byte arrays will be stored. Can be absolute or relative.
     * @param converter implements {@link KeyConverter} stategy to convert key to/from String and makes file name from it.
     * @throws IOException
     */
    public FileStore(String workingDir, KeyConverter<K, String> converter) throws IOException {
        this.dir = Paths.get(workingDir);
        this.converter = converter;

        //create directory if needed
        if (!Files.isDirectory(dir)) {
            Files.createDirectory(dir);
        }

        //scan for files
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file: stream) {
                keys.add(converter.keyFrom(file.getFileName().toString()));
            }
        }
    }

    @Override
    public byte[] load(K key) {
        byte[] result = null;
        if (keys.contains(key)) {
            Path file = fileFromKey(key);
            try {
                result = Files.readAllBytes(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void save(K key, byte[] buffer) {
        Path file = fileFromKey(key);
        try {
            Files.write(file, buffer);
            keys.add(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int length() {
        return keys.size();
    }

    @Override
    public Collection<K> getKeys() {
        return keys;
    }

    @Override
    public boolean contains(K key) {
        return keys.contains(key);
    }

    @Override
    public void remove(K key) {
        Path file = fileFromKey(key);
        try {
            Files.delete(file);
            keys.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        for (K key : keys) {
            remove(key);
        }
    }

    protected Path fileFromKey(K key) {
        return dir.resolve(converter.keyTo(key));
    }

    protected Set<K> createSet() {
        return new HashSet<>();
    }
}
