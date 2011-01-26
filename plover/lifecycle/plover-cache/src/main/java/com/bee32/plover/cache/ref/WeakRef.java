package com.bee32.plover.cache.ref;

import java.lang.ref.WeakReference;

import com.bee32.plover.cache.AbstractCacheAllocator;

public class WeakRef<T>
        implements CacheRef<T> {

    private WeakReference<T> ref;

    public WeakRef(T obj) {
        this.ref = new WeakReference<T>(obj);
    }

    @Override
    public boolean isCacheSet() {
        return ref.get() != null;
    }

    @Override
    public T getCache() {
        return ref.get();
    }

    @Override
    public void setCache(T value) {
        ref = new WeakReference<T>(value);
    }

    @Override
    public void clearCache() {
        ref.clear();
    }

    static class Allocator
            extends AbstractCacheAllocator {

        @Override
        public <T> CacheRef<T> newCache(T initialValue) {
            return new WeakRef<T>(initialValue);
        }

    }

    private static final Allocator allocator = new Allocator();

    public static Allocator getAllocator() {
        return allocator;
    }

}
