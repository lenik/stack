package com.bee32.sem.hr.util;

import javax.free.IntegerComparator;

import com.bee32.plover.collections.map.RangeFromMap;

public class ScoreLevelMap
        extends RangeFromMap<Integer, String> {

    private static final long serialVersionUID = 1L;

    public ScoreLevelMap() {
        super(IntegerComparator.INSTANCE);
    }

    public String encode() {
        return "";
    }

    public static ScoreLevelMap decode(String encoded) {
        return null;
    }

}
