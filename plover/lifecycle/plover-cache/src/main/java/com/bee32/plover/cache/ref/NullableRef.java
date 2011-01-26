package com.bee32.plover.cache.ref;

import com.bee32.plover.cache.AbstractCacheAllocator;

public class NullableRef<T>
        implements CacheRef<T> {

    private boolean isSet;
    private T value;

    public NullableRef() {
    }

    public NullableRef(T value) {
        this.value = value;
        this.isSet = true;
    }

    @Override
    public boolean isCacheSet() {
        return isSet;
    }

    @Override
    public T getCache() {
        return value;
    }

    @Override
    public synchronized void setCache(T value) {
        this.value = value;
        this.isSet = true;
    }

    @Override
    public synchronized void clearCache() {
        this.value = null;
        this.isSet = false;
    }

    static class Allocator
            extends AbstractCacheAllocator {

        @Override
        public <T> CacheRef<T> newCache() {
            return new NullableRef<T>();
        }

        @Override
        public <T> CacheRef<T> newCache(T initialValue) {
            return new NullableRef<T>(initialValue);
        }

    }

    private static final Allocator allocator = new Allocator();

    public static Allocator getAllocator() {
        return allocator;
    }

}
