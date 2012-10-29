package com.bee32.sem.attendance.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.attendance.dto.AttendanceMRecordDto;
import com.bee32.sem.attendance.entity.AttendanceMRecord;
import com.bee32.sem.attendance.entity.AttendanceType;
import com.bee32.sem.attendance.util.AttendanceCriteria;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceDAdmin
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    Date targetDate = new Date();
    int day = SalaryDateUtil.getDayNum(targetDate);
    List<AttendanceMRecordDto> attendances = new ArrayList<AttendanceMRecordDto>();

    public AttendanceDAdmin() {
        test();
    }

    void test() {

        int yearMonth = SalaryDateUtil.convertToYearMonth(targetDate);

        List<EmployeeInfoDto> employees = mrefList(EmployeeInfo.class, EmployeeInfoDto.class, 0);
        for (EmployeeInfoDto employee : employees) {
            AttendanceMRecordDto dto = null;
            if (AttendanceMAdmin.isMonthRecordExists(employee.getId(), yearMonth)) {
                AttendanceMRecord record = DATA(AttendanceMRecord.class).getUnique(
                        AttendanceCriteria.listRecordByEmployee(employee.getId(), yearMonth));
                dto = DTOs.marshal(AttendanceMRecordDto.class, record);
            } else {
                dto = new AttendanceMRecordDto().create();
                dto.setEmployee(employee);
                dto.setYear(yearMonth / 100);
                dto.setMonth(yearMonth % 100);
                dto.setAttendanceData(//
                AttendanceMRecordDto.generatorDefaultAttendanceData(yearMonth / 100, yearMonth % 100));
            }
            attendances.add(dto);
        }

    }

    public List<AttendanceType> getAttendanceTypes() {
        return new ArrayList<AttendanceType>(AttendanceType.values());
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public SelectableList<AttendanceMRecordDto> getAttendances() {
        return SelectableList.decorate(attendances);
    }

    public void setAttendances(List<AttendanceMRecordDto> attendances) {
        this.attendances = attendances;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

}
