package com.bee32.sem.attendance.util;

import java.io.Serializable;

import com.bee32.sem.attendance.entity.AttendanceType;

public class AttendanceDRecord
        implements Serializable {

    private static final long serialVersionUID = 1L;

    int day;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (day < 10)
            sb.append('0');
        sb.append(day);
        sb.append(morning.getValue());
        sb.append('.');
        sb.append(afternoon.getValue());
        sb.append('.');
        sb.append(evening.getValue());
        sb.append('.');
        sb.append('.');
        sb.append(',');
        return sb.toString();
    }

}
