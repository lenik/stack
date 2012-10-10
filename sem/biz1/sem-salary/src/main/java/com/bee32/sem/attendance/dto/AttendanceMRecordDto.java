package com.bee32.sem.attendance.dto;

import java.util.Map;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.attendance.entity.AttendanceMRecord;
import com.bee32.sem.hr.dto.EmployeeInfoDto;

public class AttendanceMRecordDto
        extends UIEntityDto<AttendanceMRecord, Long> {

    private static final long serialVersionUID = 1L;

    int year;
    int month;
    EmployeeInfoDto employee;
    boolean safe;
    Map<Integer, AttendanceDRecord> records;

    @Override
    protected void _marshal(AttendanceMRecord source) {
        year = source.getYearMonth() / 100;
        month = source.getYearMonth() % 100;
        employee = mref(EmployeeInfoDto.class, source.getEmployee());
        records = source.warpToAttendanceMap();
    }

    @Override
    protected void _unmarshalTo(AttendanceMRecord target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public AttendanceDRecord getDayRecord(int day){
        return records.get(day);
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

    public EmployeeInfoDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeInfoDto employee) {
        this.employee = employee;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public Map<Integer, AttendanceDRecord> getRecoreds() {
        return records;
    }

    public void setRecoreds(Map<Integer, AttendanceDRecord> recoreds) {
        this.records = recoreds;
    }

}
