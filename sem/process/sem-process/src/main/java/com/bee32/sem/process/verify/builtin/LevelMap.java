package com.bee32.sem.process.verify.builtin;

import java.util.Map;
import java.util.TreeMap;

import javax.free.LongComparator;

import com.bee32.plover.collections.map.RangeToMap;

/**
 * 级区映射
 *
 * <p lang="en">
 * Level Map
 */
public class LevelMap
        extends RangeToMap<Long, SingleVerifierLevel> {

    private static final long serialVersionUID = 1L;

    public LevelMap(Map<? extends Long, ? extends SingleVerifierLevel> map) {
        super(LongComparator.INSTANCE, map);
    }

    public LevelMap(TreeMap<Long, SingleVerifierLevel> treeMap) {
        super(LongComparator.INSTANCE, treeMap);
    }

    public LevelMap() {
        super(LongComparator.INSTANCE);
    }

}
