package com.bee32.zebra.tk.collections.map;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public abstract class RangeNearMap<V, T>
        extends RangeMap<V, T> {

    private static final long serialVersionUID = 1L;

    public RangeNearMap(Comparator<? super V> comparator, Map<? extends V, ? extends T> map) {
        super(comparator, map);
    }

    public RangeNearMap(Comparator<? super V> comparator, TreeMap<V, T> treeMap) {
        super(comparator, treeMap);
    }

    public RangeNearMap(Comparator<? super V> comparator) {
        super(comparator);
    }

    @Override
    public V narrowDown(V floatPoint) {
        V floorKey = floorKey(floatPoint);
        V ceilingKey = ceilingKey(floatPoint);

        if (floorKey == null)
            return ceilingKey;
        if (ceilingKey == null)
            return floorKey;

        V diffFloor = diff(floorKey, floatPoint);
        V diffCeiling = diff(floatPoint, ceilingKey);

        int cmp = comparator.compare(diffFloor, diffCeiling);

        if (cmp <= 0)
            return floorKey;
        else
            return ceilingKey;
    }

    protected abstract V diff(V a, V b);

}
