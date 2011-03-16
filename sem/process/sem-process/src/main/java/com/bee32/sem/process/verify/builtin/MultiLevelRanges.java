package com.bee32.sem.process.verify.builtin;

import java.util.Map;
import java.util.TreeMap;

import javax.free.LongComparator;

import com.bee32.plover.collections.map.RangeToMap;
import com.bee32.sem.process.verify.VerifyPolicy;

public class MultiLevelRanges
        extends RangeToMap<Long, VerifyPolicy<?, ?>> {

    private static final long serialVersionUID = 1L;

    public MultiLevelRanges(Map<? extends Long, ? extends VerifyPolicy<?, ?>> map) {
        super(LongComparator.getInstance(), map);
    }

    public MultiLevelRanges(TreeMap<Long, VerifyPolicy<?, ?>> treeMap) {
        super(LongComparator.getInstance(), treeMap);
    }

    public MultiLevelRanges() {
        super(LongComparator.getInstance());
    }

}
