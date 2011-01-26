package com.bee32.plover.cache.ref;

import com.bee32.plover.cache.AbstractCacheAllocator;

public class NonNullRef<T>
        implements CacheRef<T> {

    private T value;

    public NonNullRef() {
    }

    public NonNullRef(T value) {
        this.value = value;
    }

    @Override
    public boolean isCacheSet() {
        return value != null;
    }

    @Override
    public T getCache() {
        return value;
    }

    @Override
    public void setCache(T value) {
        this.value = value;
    }

    @Override
    public void clearCache() {
        this.value = null;
    }

    static class Allocator
            extends AbstractCacheAllocator {

        @Override
        public <T> CacheRef<T> newCache() {
            return new NonNullRef<T>();
        }

        @Override
        public <T> CacheRef<T> newCache(T initialValue) {
            return new NonNullRef<T>(initialValue);
        }

    }

    private static final Allocator allocator = new Allocator();

    public static Allocator getAllocator() {
        return allocator;
    }

}
