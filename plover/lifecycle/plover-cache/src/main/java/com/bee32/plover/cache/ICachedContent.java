package com.bee32.plover.cache;

public interface ICachedContent {

    /**
     * The following cache policies are taken into accounts:
     *
     * <ul>
     * <li>RW-through/RW-back?
     * </ul>
     */
    ICacheAllocator getCachePolicy();

    void setCachePolicy(ICacheAllocator cachePolicy);

    /**
     * Load from the persistent store.
     */
    void load()
            throws CacheException;

    /**
     * Save to the persistent store.
     */
    void save()
            throws CacheException;

    /**
     * Discard
     */
    void discard()
            throws CacheException;

}
