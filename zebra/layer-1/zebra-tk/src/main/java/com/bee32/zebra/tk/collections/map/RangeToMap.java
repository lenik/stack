package com.bee32.zebra.tk.collections.map;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class RangeToMap<X, T>
        extends RangeMap<X, T> {

    private static final long serialVersionUID = 1L;

    public RangeToMap(Comparator<? super X> comparator, Map<? extends X, ? extends T> map) {
        super(comparator, map);
    }

    public RangeToMap(Comparator<? super X> comparator, TreeMap<X, T> treeMap) {
        super(comparator, treeMap);
    }

    public RangeToMap(Comparator<? super X> comparator) {
        super(comparator);
    }

    @Override
    public X narrowDown(X floatPoint) {
        return ceilingKey(floatPoint);
    }

}
