package com.bee32.zebra.tk.collections.map;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class RangeFromMap<X, T>
        extends RangeMap<X, T> {

    private static final long serialVersionUID = 1L;

    public RangeFromMap(Comparator<? super X> comparator, Map<? extends X, ? extends T> map) {
        super(comparator, map);
    }

    public RangeFromMap(Comparator<? super X> comparator, TreeMap<X, T> treeMap) {
        super(comparator, treeMap);
    }

    public RangeFromMap(Comparator<? super X> comparator) {
        super(comparator);
    }

    @Override
    public X narrowDown(X floatPoint) {
        return treeMap.floorKey(floatPoint);
    }

}
