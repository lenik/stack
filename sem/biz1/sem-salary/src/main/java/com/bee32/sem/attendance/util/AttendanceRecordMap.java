package com.bee32.sem.attendance.util;

import java.util.Comparator;

import com.bee32.plover.collections.map.RangeFromMap;

public class AttendanceRecordMap
        extends RangeFromMap<Integer, AttendanceModel> {

    public AttendanceRecordMap(Comparator<? super Integer> comparator) {
        super(comparator);
    }

    private static final long serialVersionUID = 1L;

    public static String encode() {
        return null;
    }

    public static AttendanceRecordMap decode(String encoded) {
        return null;
    }

}
