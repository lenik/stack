package com.bee32.sem.attendance.dto;

import java.util.Date;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.attendance.entity.AttendanceDR;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.wage.util.WageDateUtil;

public class AttendanceDRDto
        extends UIEntityDto<AttendanceDR, Long> {

    private static final long serialVersionUID = 1L;

    int dayNum;
    Date date = new Date();
    EmployeeInfoDto employee;

    /**
     * 是否应出勤
     */
    AttendanceTypeDto attType;
    AttendanceTypeDto absType;
    double overtime;
    double abstime;

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    @Override
    protected void _marshal(AttendanceDR source) {
        dayNum = WageDateUtil.getDayNum(date);
        date = source.getDate();
        employee = mref(EmployeeInfoDto.class, source.getEmployee());
        attType = mref(AttendanceTypeDto.class, source.getAttType());
        absType = mref(AttendanceTypeDto.class, source.getAbsType());
        overtime = source.getOvertime();
        abstime = source.getAbstime();
    }

    @Override
    protected void _unmarshalTo(AttendanceDR target) {
        target.setDate(date);
        merge(target, "employee", employee);
        merge(target, "attType", attType);
        merge(target, "absType", absType);
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

    public AttendanceTypeDto getAttType() {
        return attType;
    }

    public void setAttType(AttendanceTypeDto attType) {
        this.attType = attType;
    }

    public AttendanceTypeDto getAbsType() {
        return absType;
    }

    public void setAbsType(AttendanceTypeDto absType) {
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
