/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

import cache.KeyGenerator;
import cache.ObjectFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class for testing {@link ObjectFactory} implementations by compare values from tested
 * and etalon implementations.
 * @param <K> type of the key
 * @param <V> type of the value
 */
public class TestObjectFactory<K, V> implements Runnable {

    protected ObjectFactory<K, V> testedFactory;
    protected ObjectFactory<K, V> etalonFactory;
    protected KeyGenerator<K> keyGenerator;
    protected int callsNumber;
    protected int threadsNumber;

    public TestObjectFactory(ObjectFactory<K, V> testedFactory, ObjectFactory<K, V> etalonFactory, KeyGenerator<K> keyGenerator,
                             int callsNumber, int threadsNumber) {
        this.testedFactory = testedFactory;
        this.etalonFactory = etalonFactory;
        this.keyGenerator = keyGenerator;
        this.callsNumber = callsNumber;
        this.threadsNumber = threadsNumber;
    }

    @Override
    public void run() {
        List<Thread> threads = new ArrayList<>(threadsNumber);
        //create and run inspectors
        for (int i = 1; i <= threadsNumber; i++) {
            Thread inspector = new Thread(new Inspector());
            threads.add(inspector);
            inspector.start();
        }
        //join inspectors threads
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private class Inspector implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= callsNumber; i++) {
                K key = keyGenerator.getKey();
                V etalon = etalonFactory.getObject(key);
                V tested = testedFactory.getObject(key);
                if (!etalon.equals(tested)) {
                    synchronized (mismatches) {
                        mismatches.add(new Mismatch<>(key, etalon, tested));
                    }
                }
            }
        }
    }

    public int totalCalls() {
        return callsNumber * threadsNumber;
    }

    protected Collection<Mismatch<K, V>> mismatches = new ArrayList<>();

    public Collection<Mismatch<K, V>> getMismatches() {
        return mismatches;
    }

    public static class Mismatch<A, B> {
        public A key;
        public B etalonValue;
        public B testedValue;

        public Mismatch(A key, B etalonValue, B testedValue) {
            this.key = key;
            this.etalonValue = etalonValue;
            this.testedValue = testedValue;
        }

        @Override
        public String toString() {
            return "Mismatch{" +
                    "key=" + key +
                    ", etalonValue=" + etalonValue +
                    ", testedValue=" + testedValue +
                    '}';
        }
    }
}
