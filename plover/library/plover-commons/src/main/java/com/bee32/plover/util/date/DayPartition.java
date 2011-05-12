package com.bee32.plover.util.date;

import java.util.Date;
import java.util.Iterator;

import javax.free.PrefetchedIterator;

public class DayPartition {

    public final DayPart[] parts;

    public DayPartition(int... offsetMinutes) {
        if (offsetMinutes == null)
            throw new NullPointerException("offsetMinutes");

        if (offsetMinutes.length < 2)
            throw new IllegalArgumentException("At least two offsets should be specified.");

        parts = new DayPart[offsetMinutes.length];
        for (int i = 0; i < offsetMinutes.length; i++) {
            int off = offsetMinutes[i];
            int next = offsetMinutes[(i + 1) % offsetMinutes.length];
            int len = (next - off + 1440) % 1440;

            DayPart part = new DayPart(this, i, off * 60000, len * 60000);
            parts[i] = part;
        }
    }

    public DateWithDayPart round(Date date) {
        for (int i = 0; i < parts.length; i++)
            if (parts[i].contains(date)) {
                long time = date.getTime();
                time -= time % DayPart.DAY_SIZE;
                Date _date = new Date(time);
                return new DateWithDayPart(_date, parts[i]);
            }
        return null;
    }

    public Iterable<DateWithDayPart> iterate(final Date from, final Date to) {
        return new Iterable<DateWithDayPart>() {
            @Override
            public Iterator<DateWithDayPart> iterator() {
                return new PrefetchedIterator<DateWithDayPart>() {

                    DateWithDayPart next;
                    DateWithDayPart end;
                    {
                        next = round(from);
                        end = round(to);
                    }

                    @Override
                    protected DateWithDayPart fetch() {
                        if (next.compareTo(end) > 0)
                            return end();

                        DateWithDayPart d = next;
                        next = next.next();

                        return d;
                    }
                };
            }
        };
    }

}
