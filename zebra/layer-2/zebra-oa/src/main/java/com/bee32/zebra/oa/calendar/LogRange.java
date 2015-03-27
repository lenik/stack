package com.bee32.zebra.oa.calendar;

import java.sql.Date;
import java.util.Calendar;

public class LogRange {

    public static final int MONTH = 1;
    public static final int WEEK = 2;

    private int view = MONTH;
    private Date start;
    private Date end;

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    /**
     * inclusive.
     */
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setStart(java.util.Date start) {
        if (start == null)
            this.start = null;
        else
            this.start = new Date(start.getTime());
    }

    /**
     * inclusive.
     */
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setEnd(java.util.Date end) {
        if (end == null)
            this.end = null;
        else
            this.end = new Date(end.getTime());
    }

    @Override
    public String toString() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        if (view == MONTH) {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            return String.format("%d 年 %d 月", year, month + 1);
        } else {
            int year = calendar.get(Calendar.YEAR);
            int week = calendar.get(Calendar.WEEK_OF_YEAR);
            return String.format("%d 年第 %d 周", year, week + 1);
        }
    }

}
