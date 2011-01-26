package com.bee32.plover.cache;

import com.bee32.plover.cache.ref.CacheRef;

public interface ICacheAllocator {

    <T> CacheRef<T> newCache();

    <T> CacheRef<T> newCache(T initialValue);

}
