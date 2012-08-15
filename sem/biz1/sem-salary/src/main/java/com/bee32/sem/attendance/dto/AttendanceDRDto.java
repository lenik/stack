package com.bee32.sem.attendance.dto;

import java.util.Date;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.attendance.entity.AttendanceDR;
import com.bee32.sem.attendance.entity.AttendanceType;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceDRDto
        extends UIEntityDto<AttendanceDR, Long> {

    private static final long serialVersionUID = 1L;

    int dayNum;
    Date date = new Date();
    EmployeeInfoDto employee;

    /**
     * 是否应出勤
     */
    AttendanceType attType;
    AttendanceType absType;
    double overtime;
    double abstime;

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    @Override
    protected void _marshal(AttendanceDR source) {
        dayNum = SalaryDateUtil.getDayNum(date);
        date = source.getDate();
        employee = mref(EmployeeInfoDto.class, source.getEmployee());
        attType = source.getAttType();
        absType = source.getAbsType();
        overtime = source.getOvertime();
        abstime = source.getAbstime();
    }

    @Override
    protected void _unmarshalTo(AttendanceDR target) {
        target.setDate(date);
        merge(target, "employee", employee);
        target.setAttType(attType);
        target.setAbsType(absType);
        target.setOvertime(overtime);
        target.setAbstime(abstime);
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EmployeeInfoDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeInfoDto employee) {
        this.employee = employee;
    }

    public AttendanceType getAttType() {
        return attType;
    }

    public void setAttType(AttendanceType attType) {
        this.attType = attType;
    }

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
