package com.bee32.plover.util.date;

public class DayDivision {

    final DayPartInterval[] intervals;

    public DayDivision(int... offsetMinutes) {
        if (offsetMinutes == null)
            throw new NullPointerException("offsetMinutes");

        if (offsetMinutes.length < 2)
            throw new IllegalArgumentException("At least two offsets should be specified.");

        intervals = new DayPartInterval[offsetMinutes.length];
        for (int i = 0; i < offsetMinutes.length; i++) {
            int off = offsetMinutes[i];
            int next = offsetMinutes[(i + 1) % offsetMinutes.length];
            int len = (next - off + 1440) % 1440;

            DayPartInterval interval = new DayPartInterval(off * 60000, len * 60000);
            intervals[i] = interval;
        }
    }

}
