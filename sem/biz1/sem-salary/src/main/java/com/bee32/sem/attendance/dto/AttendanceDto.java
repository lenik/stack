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

    int dayNum;
    Date date = new Date();
    EmployeeInfoDto employee;

    AttendanceType morning;
    AttendanceType afternoon;
    AttendanceType evening;

    int overworkTime;
    int absentTime;

    @Override
    protected void _marshal(Attendance source) {
        dayNum = SalaryDateUtil.getDayNum(date);
        date = source.getDate();
        employee = mref(EmployeeInfoDto.class, source.getEmployee());

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
