package com.bee32.sem.misc;

import java.util.Calendar;
import java.util.Date;

public enum DateRangeTemplate {

    today(true, Calendar.DATE),

    thisMonth(true, Calendar.MONTH),

    recentWeek(7),

    recentMonth(30),

    recentThreeMonths(90),

    recentYear(365),

    ;

    final boolean truncated;
    final int element;
    final long duration;

    private DateRangeTemplate(boolean truncated, int elementOrDays) {
        this.truncated = truncated;
        if (truncated) {
            element = elementOrDays;
            duration = 0;
        } else {
            element = 0;
            duration = ((long) elementOrDays) * 365 * 24 * 60 * 60 * 1000;
        }
    }

    private DateRangeTemplate(int days) {
        this(false, days);
    }

    public int getIndex() {
        return ordinal();
    }

    public String getLabel() {
        switch (this) {
        case today:
            return "今天";
        case thisMonth:
            return "这个月";
        case recentWeek:
            return "最近一个星期";
        case recentMonth:
            return "最近一个月";
        case recentThreeMonths:
            return "最近三个月";
        case recentYear:
            return "最近一年";
        }
        return "???";
    }

    public Date getBegin(Date date) {
        if (truncated) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            truncateBegin(calendar);
            return calendar.getTime();
        } else {
            return new Date(date.getTime() - duration);
        }
    }

    public Date getEnd(Date date) {
        if (truncated) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            truncateBegin(calendar);
            switch (element) {
            case Calendar.YEAR:
                calendar.add(Calendar.YEAR, 1);
                break;
            case Calendar.MONTH:
                calendar.add(Calendar.MONTH, 1);
                break;
            case Calendar.DATE:
                calendar.add(Calendar.DATE, 1);
                break;
            }
            return calendar.getTime();
        } else {
            return date;
        }
    }

    void truncateBegin(Calendar calendar) {
        switch (element) {
        case Calendar.YEAR:
            calendar.set(Calendar.MONTH, 0);
        case Calendar.MONTH:
            calendar.set(Calendar.DATE, 0);
        case Calendar.DATE:
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }
    }

}
