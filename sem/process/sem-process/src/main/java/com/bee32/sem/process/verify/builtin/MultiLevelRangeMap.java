package com.bee32.sem.process.verify.builtin;

import java.util.Map;
import java.util.TreeMap;

import javax.free.LongComparator;

import com.bee32.plover.collections.map.RangeToMap;

public class MultiLevelRangeMap
        extends RangeToMap<Long, MultiLevelRange> {

    private static final long serialVersionUID = 1L;

    public MultiLevelRangeMap(Map<? extends Long, ? extends MultiLevelRange> map) {
        super(LongComparator.getInstance(), map);
    }

    public MultiLevelRangeMap(TreeMap<Long, MultiLevelRange> treeMap) {
        super(LongComparator.getInstance(), treeMap);
    }

    public MultiLevelRangeMap() {
        super(LongComparator.getInstance());
    }

}
