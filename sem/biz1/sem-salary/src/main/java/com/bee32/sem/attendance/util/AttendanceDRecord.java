package com.bee32.sem.attendance.util;

import java.io.Serializable;

import com.bee32.sem.attendance.entity.AttendanceType;

public class AttendanceDRecord
        implements Serializable {

    private static final long serialVersionUID = 1L;

    int day;
    String weekday_zhcn;
    String styleClass;

    AttendanceType morning;
    AttendanceType afternoon;
    AttendanceType evening;

    public AttendanceDRecord() {
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getWeekday_zhcn() {
        return weekday_zhcn;
    }

    public void setWeekday_zhcn(String weekday_zhcn) {
        this.weekday_zhcn = weekday_zhcn;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public AttendanceType getMorning() {
        return morning;
    }

    public void setMorning(AttendanceType morning) {
        this.morning = morning;
    }

    public AttendanceType getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(AttendanceType afternoon) {
        this.afternoon = afternoon;
    }

    public AttendanceType getEvening() {
        return evening;
    }

    public void setEvening(AttendanceType evening) {
        this.evening = evening;
    }

}
