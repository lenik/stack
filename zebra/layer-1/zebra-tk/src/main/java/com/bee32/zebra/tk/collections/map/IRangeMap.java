package com.bee32.zebra.tk.collections.map;

import java.util.NavigableMap;

/**
 * Map entity divided in sub-ranges.
 *
 * @param <V>
 *            Range boundary value type
 * @param <T>
 *            The foreign key for the target entity.
 */
public interface IRangeMap<V, T>
        extends NavigableMap<V, T> {

    /**
     * Get the defined boundary of the range which contains the <code>floatPoint</code>.
     *
     * @param floatPoint
     *            Search point.
     * @return Any defined target for the range which includes the <code>floatPoint</code>,
     *         <code>null</code> if the point is undefined.
     */
    V narrowDown(V floatPoint);

    /**
     * Get the precise target for the range boundary.
     *
     * @param boundaryPoint
     *            The range boundary.
     * @return The defined target for the range aligned at <code>boundaryPoint</code>.
     *         <code>null</code> if the range is undefined.
     */
    T getNarrowedTarget(V boundaryPoint);

    /**
     * The same as:
     *
     * <pre>
     * getNarrowedTarget(narrowDown(floatPoint))
     * </pre>
     *
     * @param floatPoint
     *            Search point.
     * @return Any defined target for the range which includes the <code>floatPoint</code>,
     *         <code>null</code> if the point is undefined.
     */
    T getTarget(V floatPoint);

}
