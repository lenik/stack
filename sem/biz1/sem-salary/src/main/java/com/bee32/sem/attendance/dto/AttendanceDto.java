package com.bee32.sem.attendance.dto;

import java.util.Date;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.attendance.entity.Attendance;
import com.bee32.sem.attendance.entity.AttendanceType;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceDto
        extends UIEntityDto<Attendance, Long> {

    private static final long serialVersionUID = 1L;

    Date date = new Date();
    EmployeeInfoDto employee;
    int year;
    int month;
    int day;

    AttendanceType morning;
    AttendanceType afternoon;
    AttendanceType evening;

    int overworkTime;
    int absentTime;

    @Override
    protected void _marshal(Attendance source) {
        date = source.getDate();
        employee = mref(EmployeeInfoDto.class, source.getEmployee());
        year = source.getYear();
        month = source.getMonth();
        day = source.getDay();

        morning = source.getMorning();
        afternoon = source.getAfternoon();
        evening = source.getEvening();

        overworkTime = source.getOverworkTime();
        absentTime = source.getAbsentTime();
    }

    @Override
    protected void _unmarshalTo(Attendance target) {
        target.setDate(date);
        merge(target, "employee", employee);

        target.setMorning(morning);
        target.setAfternoon(afternoon);
        target.setEvening(evening);

        target.setOverworkTime(overworkTime);
        target.setAbsentTime(absentTime);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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

    public int getOverworkTime() {
        return overworkTime;
    }

    public void setOverworkTime(int overworkTime) {
        this.overworkTime = overworkTime;
    }

    public int getAbsentTime() {
        return absentTime;
    }

    public void setAbsentTime(int absentTime) {
        this.absentTime = absentTime;
    }

    public String getDateString() {
        return SalaryDateUtil.getDayString(date);
    }

}
