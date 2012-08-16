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

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "attendance_d_seq", allocationSize = 1)
public class AttendanceDR
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Date date;
    EmployeeInfo employee;

    AttendanceType morning = AttendanceType.normal;
    AttendanceType afternoon = AttendanceType.normal;
    AttendanceType evening = AttendanceType.normal;

    double overtime;
    double abstime;

    public AttendanceDR() {
        super();
    }

    @Temporal(TemporalType.DATE)
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

    @Column(length = 10, nullable = false)
    public String get_morning() {
        return morning.getValue();
    }

    public void set_morning(String _morning) {
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

    public double getOvertime() {
        return overtime;
    }

    public void setOvertime(double overtime) {
        this.overtime = overtime;
    }

    public double getAbstime() {
        return abstime;
    }

    public void setAbstime(double abstime) {
        this.abstime = abstime;
    }

}
