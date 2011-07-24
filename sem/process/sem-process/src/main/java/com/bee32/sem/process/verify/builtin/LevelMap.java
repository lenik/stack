package com.bee32.sem.process.verify.builtin;

import java.util.Map;
import java.util.TreeMap;

import javax.free.LongComparator;

import com.bee32.plover.collections.map.RangeToMap;

public class LevelMap
        extends RangeToMap<Long, MultiLevel> {

    private static final long serialVersionUID = 1L;

    public LevelMap(Map<? extends Long, ? extends MultiLevel> map) {
        super(LongComparator.INSTANCE, map);
    }

    public LevelMap(TreeMap<Long, MultiLevel> treeMap) {
        super(LongComparator.INSTANCE, treeMap);
    }

    public LevelMap() {
        super(LongComparator.INSTANCE);
    }

}
