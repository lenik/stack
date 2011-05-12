package com.bee32.plover.util.date;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateWithDayPart
        implements Comparable<DateWithDayPart>, Serializable {

    private static final long serialVersionUID = 1L;

    final Date _date; // date-only
    final DayPart part;

    DateWithDayPart(Date _date, DayPart part) {
        if (_date == null)
            throw new NullPointerException("_date");
        if (part == null)
            throw new NullPointerException("part");

        this._date = _date; // date.getTime() / DayPart.DAY_SIZE;
        this.part = part;
    }

    public Date getTruncatedDate() {
        return _date;
    }

    public DayPart getPart() {
        return part;
    }

    public Date getDateOfPartBegin() {
        return part.getStartDate(_date);
    }

    public Date getDateOfPartEnd() {
        return part.getEndDate(_date);
    }

    public DateWithDayPart next() {
        DayPart nextPart = part.getNext();
        Date nextDate = _date;

        if (nextPart.isFirst())
            nextDate = new Date(_date.getTime() + DayPart.DAY_SIZE);

        return new DateWithDayPart(nextDate, nextPart);
    }

    @Override
    public int compareTo(DateWithDayPart other) {
        if (this == other)
            return 0;

        if (other == null)
            return 1;

        int cmp = this._date.compareTo(other._date);
        if (cmp != 0)
            return cmp;

        cmp = part.compareTo(other.part);
        return cmp;
    }

    static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String toString() {
        return DATE_FORMAT.format(_date) + "[" + part + "]";
    }

}
