/*
 * Copyright (c) 2021.  Topjoy all rights reserved.
 */

package com.xcompany.nimble.biz.numeric;


import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 策划数值
 *
 * @author xiadong
 * @since 2021/09/06
 **/
@SuppressWarnings("unchecked")
@Log4j2
public class Numerics {
    private static final ReentrantReadWriteLock RW_LOCK = new ReentrantReadWriteLock();
    static Map<Class<? extends NumericTable>, Map<Integer, ? extends NumericTable>> DATA = new HashMap<>();

    public static <T extends NumericTable, R> Optional<R> get(Class<T> clz, Function<T, R> getter, int id) {
        readLock();
        try {
            return get(clz, id)
                    .map(getter);
        } finally {
            releaseReadLock();
        }
    }

    public static <T extends NumericTable> Optional<T> get(Class<T> clz, int id) {
        readLock();
        try {
            return Optional.ofNullable((T) DATA.getOrDefault(clz, Collections.emptyMap()).get(id));
        } finally {
            releaseReadLock();
        }
    }

    public static @NonNull <T extends NumericTable> Map<Integer, T> get(Class<T> clz) {
        readLock();
        try {
            return (Map<Integer, T>) DATA.getOrDefault(clz, Collections.emptyMap());
        } finally {
            releaseReadLock();
        }
    }

    public static @NonNull <T extends NumericTable> Stream<T> stream(Class<T> clz) {
        readLock();
        try {
            return ((Map<Integer, T>) DATA.getOrDefault(clz, Collections.emptyMap())).values().stream();
        } finally {
            releaseReadLock();
        }
    }

    public static boolean exists(Class<? extends NumericTable> clz, int id) {
        readLock();
        try {
            return DATA.getOrDefault(clz, Collections.emptyMap()).containsKey(id);
        } finally {
            releaseReadLock();
        }
    }

    static void init(Map<Class<? extends NumericTable>, Map<Integer, ? extends NumericTable>> data) {
        DATA = data;
    }

    /**
     * for test only!
     *
     * @param clz        class
     * @param testValues test values
     */
    public static void partialInit(Class<? extends NumericTable> clz, Map<Integer, ? extends NumericTable> testValues) {
        if (testValues.isEmpty()) {
            log.warn("Inject empty values!");
            return;
        }
        Map<Class<? extends NumericTable>, Map<Integer, ? extends NumericTable>> data = new HashMap<>(testValues.size() * 2);
        data.put(clz, testValues);
        DATA.putAll(data);
    }

    static void readLock() {
        RW_LOCK.readLock().lock();
    }

    static void writeLock() {
        RW_LOCK.writeLock().lock();
    }

    static void releaseReadLock() {
        RW_LOCK.readLock().unlock();
    }

    static void releaseWriteLock() {
        RW_LOCK.writeLock().unlock();
    }
}