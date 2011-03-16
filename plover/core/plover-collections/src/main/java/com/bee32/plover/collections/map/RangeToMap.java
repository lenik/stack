package com.bee32.plover.collections.map;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class RangeToMap<V, T>
        extends RangeMap<V, T> {

    private static final long serialVersionUID = 1L;

    public RangeToMap(Comparator<? super V> comparator, Map<? extends V, ? extends T> map) {
        super(comparator, map);
    }

    public RangeToMap(Comparator<? super V> comparator, TreeMap<V, T> treeMap) {
        super(comparator, treeMap);
    }

    public RangeToMap(Comparator<? super V> comparator) {
        super(comparator);
    }

    @Override
    public V narrowDown(V floatPoint) {
        return ceilingKey(floatPoint);
    }

}
