package com.bee32.plover.cache;

import com.bee32.plover.cache.ref.CacheRef;

public abstract class AbstractCacheAllocator
        implements ICacheAllocator {

    @Override
    public <T> CacheRef<T> newCache() {
        return newCache(null);
    }

}
