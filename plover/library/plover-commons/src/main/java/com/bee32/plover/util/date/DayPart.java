package com.bee32.plover.util.date;

import java.io.Serializable;
import java.util.Date;

public final class DayPart
        implements Comparable<DayPart>, Serializable {

    private static final long serialVersionUID = 1L;

    static final int DAY_SIZE = 86400000;

    private String name;

    private final int start;
    private final int end;

    private DayPartition partition;
    private final int index;

    /**
     * @param offset
     *            Day offset in milliseconds.
     * @param length
     *            Duration length in milliseconds.
     */
    public DayPart(DayPartition partition, int index, int offset, int length) {
        this.partition = partition;
        this.index = index;

        this.start = offset;
        this.end = offset + length;

        this.name = start + " - " + end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean contains(Date date) {
        int time = (int) (date.getTime() % DAY_SIZE);
        if (time < 0)
            time += DAY_SIZE;

        if (end < DAY_SIZE)
            return start <= time && time < end;
        else
            return start <= time || time < end - DAY_SIZE;
    }

    public DayPart getNext() {
        int nextIndex = (index + 1) % partition.parts.length;
        return partition.parts[nextIndex];
    }

    public boolean isFirst() {
        return index == 0;
    }

    public boolean isLast() {
        return index == partition.parts.length - 1;
    }

    public Date getStartDate(Date date) {
        long _date = date.getTime();
        long time = _date % DayPart.DAY_SIZE;
        Date startDate = new Date(_date - time + start);
        return startDate;
    }

    public Date getEndDate(Date date) {
        long _date = date.getTime();
        long time = _date % DayPart.DAY_SIZE;
        Date endDate = new Date(_date - time + end);
        return endDate;
    }

    @Override
    public int compareTo(DayPart o) {
        if (this == o)
            return 0;

        if (o == null)
            return -1;

        int cmp = index - o.index;
        return cmp;
    }

    @Override
    public String toString() {
        return name;
    }

}
