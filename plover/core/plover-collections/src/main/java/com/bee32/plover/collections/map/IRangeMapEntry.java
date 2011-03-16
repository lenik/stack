package com.bee32.plover.collections.map;

public interface IRangeMapEntry<V, T> {

    /**
     * Get the boundary point.
     *
     * @return Boundary point.
     */
    V getBoundary();

    /**
     * Get the target entity.
     *
     * @return Non-<code>null</code> target entity.
     */
    T getTarget();

}
