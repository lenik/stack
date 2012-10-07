package com.bee32.sem.attendance.dto;

import com.bee32.sem.attendance.entity.AttendanceType;

public class AttendanceDRecord {

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

}
