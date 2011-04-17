package com.bee32.sem.process.verify.builtin;

import java.util.Map;
import java.util.TreeMap;

import javax.free.LongComparator;

import com.bee32.plover.collections.map.RangeToMap;

public class LevelMap
        extends RangeToMap<Long, Level> {

    private static final long serialVersionUID = 1L;

    public LevelMap(Map<? extends Long, ? extends Level> map) {
        super(LongComparator.getInstance(), map);
    }

    public LevelMap(TreeMap<Long, Level> treeMap) {
        super(LongComparator.getInstance(), treeMap);
    }

    public LevelMap() {
        super(LongComparator.getInstance());
    }

}
