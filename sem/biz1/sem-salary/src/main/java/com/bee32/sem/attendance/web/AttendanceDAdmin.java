package com.bee32.sem.attendance.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.attendance.dto.AttendanceDto;
import com.bee32.sem.attendance.util.AttendanceCriteria;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceDAdmin
        extends EntityViewBean {

    List<AttendanceDto> attendances = new ArrayList<AttendanceDto>();

    Date openedDate = new Date();
    List<EmployeeInfoDto> allEmployees;

    public AttendanceDAdmin() {

        allEmployees = mrefList(EmployeeInfo.class, EmployeeInfoDto.class, 0);

        AttendanceCriteria.getMonthList(new Date());
        for (EmployeeInfoDto employee : allEmployees) {
            AttendanceDto attendance = new AttendanceDto().create();
            attendance.setEmployee(employee);
            attendance.setDayNum(SalaryDateUtil.getDayNum(openedDate));
            attendance.setDate(openedDate);
            attendances.add(attendance);
        }
    }

    private static final long serialVersionUID = 1L;

    public void addAttendanceRecord() {

        for (AttendanceDto attendance : attendances) {
            // TODO
        }
    }

    protected boolean save(int saveFlags, String hint) {
        for (AttendanceDto day : attendances) {

            /**
             * 如果该天的出勤记录已经存在，不去管
             */
        }

        return true;
    }

    public Date getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(Date openedDate) {
        this.openedDate = openedDate;
    }

    public List<AttendanceDto> getAttendanceDRList() {
        return attendances;
    }

    public void setAttendanceDRList(List<AttendanceDto> attendanceDRList) {
        this.attendances = attendanceDRList;
    }
}
