package com.bee32.plover.collections.map;

public interface IRangeMapEntry<X, T> {

    /**
     * Get the boundary point.
     *
     * @return Boundary point.
     */
    X getX();

    /**
     * Get the target entity.
     *
     * @return Non-<code>null</code> target entity.
     */
    T getTarget();

}
