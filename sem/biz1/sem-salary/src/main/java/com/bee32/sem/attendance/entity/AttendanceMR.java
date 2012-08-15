package com.bee32.sem.attendance.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.hr.entity.EmployeeInfo;

/**
 * 考勤记录，按月
 */
@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "attendance_m_seq", allocationSize = 1)
public class AttendanceMR
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    List<AttendanceDR> attendances;
    Date date;
    EmployeeInfo employee;

    boolean cal = false;
    boolean safety = true;

    /**
     * 三元风机 月考勤
     */
    // 应该出勤
    double should_attendance = 0.0;
    // 出勤天数
    double real_attendance = 0.0;
    // 出差天数
    double trip = 0.0;
    // 应晚加班天数
    double should_overtime = 0.0;
    // 晚加班天数
    double real_overtime = 0.0;

    double leave;

    @OneToMany
    @Cascade({ CascadeType.ALL })
    public List<AttendanceDR> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<AttendanceDR> attendances) {
        this.attendances = attendances;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne
    public EmployeeInfo getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeInfo employee) {
        this.employee = employee;
    }

    public boolean isCal() {
        return cal;
    }

    public void setCal(boolean cal) {
        this.cal = cal;
    }

    public boolean isSafety() {
        return safety;
    }

    public void setSafety(boolean safety) {
        this.safety = safety;
    }

    public double getShould_attendance() {
        return should_attendance;
    }

    public void setShould_attendance(double should_attendance) {
        this.should_attendance = should_attendance;
    }

    public double getReal_attendance() {
        return real_attendance;
    }

    public void setReal_attendance(double real_attendance) {
        this.real_attendance = real_attendance;
    }

    public double getTrip() {
        return trip;
    }

    public void setTrip(double trip) {
        this.trip = trip;
    }

    public double getShould_overtime() {
        return should_overtime;
    }

    public void setShould_overtime(double should_overtime) {
        this.should_overtime = should_overtime;
    }

    public double getReal_overtime() {
        return real_overtime;
    }

    public void setReal_overtime(double real_overtime) {
        this.real_overtime = real_overtime;
    }

    public double getLeave() {
        return leave;
    }

    public void setLeave(double leave) {
        this.leave = leave;
    }
}
