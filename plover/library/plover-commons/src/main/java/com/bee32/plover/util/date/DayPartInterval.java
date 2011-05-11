package com.bee32.plover.util.date;

import java.util.Date;
import java.util.TimeZone;

public final class DayPartInterval
        extends DateInterval {

    private int start;
    private int end;

    /**
     * @param offset
     *            Day offset in milliseconds.
     * @param length
     *            Duration length in milliseconds.
     */
    public DayPartInterval(int offset, int length) {
        this.start = offset;
        this.end = offset + length;
    }

    final int DAY_SIZE = 86400000;

    @Override
    public Date floor(TimeZone timeZone, Date date) {
        long time = date.getTime();
        int tzOffset = timeZone.getOffset(time);

        long wallDate = time + tzOffset;
        int clock = (int) (wallDate % DAY_SIZE);
        wallDate -= clock;

        if (clock < start)
            wallDate -= DAY_SIZE;

        clock = start;
        wallDate += clock;

        time = wallDate - tzOffset;
        return new Date(time);
    }

    @Override
    public Date ceil(TimeZone timeZone, Date date) {
        long time = date.getTime();
        int tzOffset = timeZone.getOffset(time);

        long wallDate = time + tzOffset;
        int clock = (int) (wallDate % DAY_SIZE);
        wallDate -= clock;

        if (clock >= end)
            wallDate += DAY_SIZE;

        clock = end;
        wallDate += clock;

        time = wallDate - tzOffset;
        return new Date(time);
    }

}
