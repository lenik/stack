package com.bee32.plover.cache.ref;

public interface CacheRef<T> {

    /**
     * Whether the cache content has been set.
     *
     * The {@link #get()} method will returns meaningful value only if the cache content has been
     * set.
     *
     * @return <code>true</code> The cache content has been set.
     */
    boolean isCacheSet();

    /**
     * Get the cache content.
     *
     * @return The content cached inside.
     */
    T getCache();

    /**
     * Set the cache content.
     *
     * @param value
     *            The content value to set into.
     */
    void setCache(T value);

    /**
     * Clear the cache content.
     */
    void clearCache();

}
