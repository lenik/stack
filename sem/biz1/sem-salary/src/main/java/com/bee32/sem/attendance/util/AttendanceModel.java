package com.bee32.sem.attendance.util;

import java.io.Serializable;

import com.bee32.sem.attendance.entity.AttendanceType;

public class AttendanceModel
        implements Serializable {

    private static final long serialVersionUID = 1L;

    int day;
    AttendanceType morning;
    AttendanceType afternoon;
    AttendanceType evening;
    int scale;

    public AttendanceModel(int day, AttendanceType morning, AttendanceType afternoon, AttendanceType evening, int scale) {
        this.day = day;
        this.morning = morning;
        this.afternoon = afternoon;
        this.evening = evening;
        this.scale = scale;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

}
