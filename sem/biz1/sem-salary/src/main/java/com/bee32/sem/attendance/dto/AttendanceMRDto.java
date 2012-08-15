package com.bee32.sem.attendance.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.attendance.entity.AttendanceMR;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceMRDto
        extends UIEntityDto<AttendanceMR, Long> {

    private static final long serialVersionUID = 1L;

    List<AttendanceDRDto> attendances;
    Date date;
    EmployeeInfoDto employee;
    boolean cal;

    boolean safety;
    /**
     * 三元风机
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

    double leave = 0.0;

    @Override
    protected void _marshal(AttendanceMR source) {
        attendances = marshalList(AttendanceDRDto.class, source.getAttendances());
        date = source.getDate();
        employee = marshal(EmployeeInfoDto.class, source.getEmployee());
        cal = source.isCal();
        safety = source.isSafety();
        should_attendance = source.getShould_attendance();
        real_attendance = source.getReal_attendance();
        trip = source.getTrip();
        should_overtime = source.getShould_overtime();
        real_overtime = source.getReal_overtime();
        leave = source.getLeave();
    }

    @Override
    protected void _unmarshalTo(AttendanceMR target) {
        mergeList(target, "attendances", attendances);
        target.setDate(date);
        merge(target, "employee", employee);
        target.setCal(cal);
        target.setSafety(safety);
        target.setShould_attendance(should_attendance);
        target.setReal_attendance(real_attendance);
        target.setTrip(trip);
        target.setShould_overtime(should_overtime);
        target.setReal_overtime(real_overtime);
        target.setLeave(leave);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public boolean isExist(AttendanceDRDto attendanced) {
        Set<Integer> set = getAttendanceIDSet();
        if (set.contains(attendanced.getDayNum()))
            return true;
        return false;
    }

    public void addDAttendance(AttendanceDRDto attendanced) {
        attendances.add(attendanced);
    }

    public List<AttendanceDRDto> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<AttendanceDRDto> attendances) {
        this.attendances = attendances;
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

    public boolean isCal() {
        return cal;
    }

    public void setCal(boolean isCal) {
        this.cal = isCal;
    }

    public boolean isSafety() {
        return safety;
    }

    public void setSafety(boolean safety) {
        this.safety = safety;
    }

    public void setEmployee(EmployeeInfoDto employee) {
        this.employee = employee;
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

    public String getDateString() {
        return SalaryDateUtil.getDateString(date);
    }

    Set<Integer> getAttendanceIDSet() {
        Set<Integer> set = new HashSet<Integer>();
        for (AttendanceDRDto attendanced : attendances) {
            set.add(attendanced.getDayNum());
        }
        return set;
    }
}
