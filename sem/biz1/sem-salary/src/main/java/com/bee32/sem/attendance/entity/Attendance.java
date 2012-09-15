package com.bee32.sem.attendance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.hr.entity.EmployeeInfo;

/**
 * 每日出勤记录。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "attendance_seq", allocationSize = 1)
public class Attendance
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Date date = new Date();
    EmployeeInfo employee;
    int year;
    int month;
    int day;

    AttendanceType morning = AttendanceType.normal;
    AttendanceType afternoon = AttendanceType.normal;
    AttendanceType evening = AttendanceType.notAvailable;

    int overworkTime;
    int absentTime;

    public Attendance() {
    }

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (date == null)
            throw new NullPointerException("date");
        this.date = date;
    }

    /**
     * 雇员
     */
    @ManyToOne(optional = false)
    public EmployeeInfo getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeInfo employee) {
        this.employee = employee;
    }

    @Transient
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Transient
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Transient
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Column(nullable = false)
    public int getYearMonthDay() {
        return year * 10000 + month * 100 + day;
    }

    public void setYearMonthDay(int yearMonthDay) {
        this.year = yearMonthDay / 10000;
        yearMonthDay %= 10000;
        this.month = yearMonthDay / 100;
        yearMonthDay %= 100;
        this.day = yearMonthDay;
    }

    /**
     * 上午出勤状态
     */
    @Column(length = 10, nullable = false)
    String get_morning() {
        return morning.getValue();
    }

    void set_morning(String _morning) {
        this.morning = AttendanceType.forName(_morning);
    }

    @Transient
    public AttendanceType getMorning() {
        return morning;
    }

    public void setMorning(AttendanceType morning) {
        if (morning == null)
            throw new NullPointerException("morning");
        this.morning = morning;
    }

    /**
     * 下午出勤状态
     */
    @Column(length = 10, nullable = false)
    String get_afternoon() {
        return afternoon.getValue();
    }

    void set_afternoon(String _afternoon) {
        this.afternoon = AttendanceType.forName(_afternoon);
    }

    @Transient
    public AttendanceType getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(AttendanceType afternoon) {
        if (afternoon == null)
            throw new NullPointerException("afternoon");
        this.afternoon = afternoon;
    }

    /**
     * 晚上出勤状态
     */
    @Column(length = 10, nullable = false)
    String get_evening() {
        return evening.getValue();
    }

    void set_evening(String _evening) {
        this.evening = AttendanceType.forName(_evening);
    }

    @Transient
    public AttendanceType getEvening() {
        return evening;
    }

    public void setEvening(AttendanceType evening) {
        if (evening == null)
            throw new NullPointerException("evening");
        this.evening = evening;
    }

    /**
     * 当日累计加班时间（分钟）
     */
    @Column(nullable = false)
    public int getOverworkTime() {
        return overworkTime;
    }

    public void setOverworkTime(int overworkTime) {
        this.overworkTime = overworkTime;
    }

    /**
     * 当日累计缺勤时间（分钟）
     */
    @Column(nullable = false)
    public int getAbsentTime() {
        return absentTime;
    }

    public void setAbsentTime(int absentTime) {
        this.absentTime = absentTime;
    }

}
