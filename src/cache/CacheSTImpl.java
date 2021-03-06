/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

package cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * Single threaded implementation of {@link Cache}.
 * Requires "Strategies": {@link CacheStore}, {@link CacheAlgorithm}, {@link ObjectSerializer}, {@link ObjectFactory}
 * @param <K> type of the key
 * @param <V> type of the value
 */
public class CacheSTImpl<K, V> implements Cache<K, V> {

    protected List<Measure<K>> measuresList = createMeasuresList();
    protected Map<K, Measure<K>> measuresMap = createMeasuresMap();

    protected CacheStore<K> store;
    protected CacheAlgorithm algorithm;
    protected ObjectSerializer<V> serializer;
    protected ObjectFactory<K, V> objectFactory;
    protected int cacheMaxElements;
    protected int removeAtOnce;

    /**
     * Creates instance of Cache.
     * @param store implements {@link CacheStore} strategy.
     * @param algorithm implements {@link CacheAlgorithm} elements displacement strategy.
     * @param serializer implements {@link ObjectSerializer} strategy.
     * @param objectFactory implements {@link ObjectFactory} strategy.
     * @param cacheMaxElements max number elements in cache.
     * If number of elements exceed this parameter some elements
     * in quantities of removeAtOnce parameter will be removed by algorithm.
     * @param removeAtOnce number elements which will be removed if total number elements
     * in cache exceed cacheMaxElements parameter.
     */
    public CacheSTImpl(CacheStore<K> store, CacheAlgorithm algorithm, ObjectSerializer<V> serializer, ObjectFactory<K, V> objectFactory,
                       int cacheMaxElements, int removeAtOnce) {
        this.store = store;
        this.algorithm = algorithm;
        this.serializer = serializer;
        this.objectFactory = objectFactory;
        this.cacheMaxElements = cacheMaxElements;
        this.removeAtOnce = removeAtOnce;

        //apply algorithm to data from store
        for (K key : store.getKeys()) {
            applyAlgorithm(key);
        }
    }

    @Override
    public V getObject(K key) {
        V object;
        if (store.contains(key)) {
            //getting object from cache store
            object = serializer.deSerializeObject(new ByteArrayInputStream(store.load(key)));
        }
        else {
            //create object from factory and save it in cache store
            object = objectFactory.getObject(key);
            //returns null if object with specified key was not created by factory
            if (object == null) {
                return null;
            }
            addToStore(key, object);
        }
        applyAlgorithm(key);

        return object;
    }

    @Override
    public void putObject(K key, V object) {
        if (store.contains(key)) {
            removeObject(key);
        }
        addToStore(key, object);
        applyAlgorithm(key);
    }

    @Override
    public void removeObject(K key) {
        store.remove(key);
        Measure measure = measuresMap.get(key);
        if (measure != null) {
            measuresList.remove(measure);
            measuresMap.remove(key);
        }
    }

    @Override
    public void clear() {
        store.clear();
        measuresList.clear();
        measuresMap.clear();
    }

    protected void addToStore(K key, V object) {
        //check if needed remove some elements from store
        if (store.length() >= cacheMaxElements) {
            Collections.sort(measuresList, measureComparator);
            //remove first removeAtOnce elements from store
            for (int i = 1; i <= removeAtOnce; i++) {
                K removeKey = measuresList.get(0).key;
                store.remove(removeKey);
                measuresMap.remove(removeKey);
                measuresList.remove(0);
            }
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        serializer.serializeObject(object, bos);
        store.save(key, bos.toByteArray());
    }

    protected void applyAlgorithm(K key) {
        Measure<K> measure = measuresMap.get(key);
        if (measure == null) {
            measure = new Measure<>(key);
            measuresMap.put(key, measure);
            measuresList.add(measure);
        }
        measure.meter = algorithm.calcMeter(measure.meter);
    }

    protected List<Measure<K>> createMeasuresList() {
        return new ArrayList<>();
    }

    protected Map<K, Measure<K>> createMeasuresMap() {
        return new HashMap<>();
    }

    protected static class Measure<T> {
        public T key;
        public long meter;

        public Measure(T key) {
            this.key = key;
        }
    }

    protected final Comparator<Measure> measureComparator = new Comparator<Measure>() {
        @Override
        public int compare(Measure o1, Measure o2) {
            if (o1.meter > o2.meter) {
                return 1;
            }
            else if (o2.meter > o1.meter) {
                return -1;
            }
            else {
                return 0;
            }
        }
    };
}
