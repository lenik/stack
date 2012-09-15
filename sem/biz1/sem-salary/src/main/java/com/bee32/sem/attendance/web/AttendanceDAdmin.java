package com.bee32.sem.attendance.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.attendance.dto.AttendanceDto;
import com.bee32.sem.attendance.entity.AttendanceType;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.salary.util.ColumnModel;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceDAdmin
        extends EntityViewBean {

    List<AttendanceDto> attendances = new ArrayList<AttendanceDto>();

    Date targetDate = new Date();
    int targetYear;
    int targetMonth;
    List<EmployeeInfoDto> allEmployees;
    List<ColumnModel> columns;

    public AttendanceDAdmin() {

        allEmployees = mrefList(EmployeeInfo.class, EmployeeInfoDto.class, 0);

// AttendanceCriteria.getMonthList(new Date());
// for (EmployeeInfoDto employee : allEmployees) {
// AttendanceDto attendance = new AttendanceDto().create();
// attendance.setEmployee(employee);
// attendance.setDate(targetDate);
// attendances.add(attendance);
// }
    }

    private static final long serialVersionUID = 1L;

    List<List<AttendanceDto>> generatorAttendanceMRecord() {
        List<List<AttendanceDto>> month_record = new ArrayList<List<AttendanceDto>>();

        int days = SalaryDateUtil.getDayNumberOfMonth(targetDate);

        for (EmployeeInfoDto employee : allEmployees) {
            List<AttendanceDto> mlist = new ArrayList<AttendanceDto>();

            for (int i = 1; i <= days; i++) {
                AttendanceDto day_attendance = new AttendanceDto();
                day_attendance.setDate(SalaryDateUtil.convertToDate(targetYear, targetMonth, i));
                day_attendance.setEmployee(employee);
                day_attendance.setMorning(AttendanceType.normal);
                day_attendance.setAfternoon(AttendanceType.normal);
                day_attendance.setEvening(AttendanceType.notAvailable);
                mlist.add(day_attendance);
            }
            month_record.add(mlist);
        }

        return month_record;
    }

    void generateColumns(){
        int columnNumber = SalaryDateUtil.getDayNumberOfMonth(targetDate);
        for(int i = 1; i <= columnNumber; i ++){

        }
    }

    public SelectableList<List<AttendanceDto>> getTest() {
        return SelectableList.decorate(generatorAttendanceMRecord());
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public List<AttendanceDto> getAttendanceDRList() {
        return attendances;
    }

    public void setAttendanceDRList(List<AttendanceDto> attendanceDRList) {
        this.attendances = attendanceDRList;
    }
}
