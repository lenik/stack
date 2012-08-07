package com.bee32.sem.attendance.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.hr.entity.EmployeeInfo;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "attendance_d_seq", allocationSize = 1)
public class AttendanceDR
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Date date;
    EmployeeInfo employee;
    /**
     * 正常出勤或者
     */
    AttendanceType attType = predefined(AttendanceTypes.class).normal;
    AttendanceType absType = predefined(AttendanceTypes.class).leave;
    double overtime;
    double abstime;

    public AttendanceDR() {
        super();
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

    @ManyToOne
    public AttendanceType getAttType() {
        return attType;
    }

    public void setAttType(AttendanceType attType) {
        this.attType = attType;
    }

    @ManyToOne
    public AttendanceType getAbsType() {
        return absType;
    }

    public void setAbsType(AttendanceType absType) {
        this.absType = absType;
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
