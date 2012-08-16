package com.bee32.sem.attendance.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bee32.sem.attendance.dto.AttendanceDto;
import com.bee32.sem.attendance.dto.AttendanceMRDto;
import com.bee32.sem.attendance.entity.AttendanceMR;
import com.bee32.sem.attendance.util.AttendanceCriteria;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceDAdmin
        extends SimpleEntityViewBean {

    List<AttendanceDto> attendanceDRList = new ArrayList<AttendanceDto>();
    Map<Long, AttendanceMRDto> amr = new HashMap<Long, AttendanceMRDto>();

    Date openedDate = new Date();
    List<EmployeeInfoDto> allEmployees;

    public AttendanceDAdmin() {
        super(EmployeeInfo.class, EmployeeInfoDto.class, 0);
        allEmployees = mrefList(EmployeeInfo.class, EmployeeInfoDto.class, 0);

        List<AttendanceMRDto> attendanceMRList = mrefList(AttendanceMR.class, AttendanceMRDto.class, 0,
                AttendanceCriteria.getMonthList(new Date()));
        if (attendanceMRList.size() == 0 || attendanceMRList.isEmpty())
            for (EmployeeInfoDto employee : allEmployees) {
                AttendanceMRDto attendanceMR = new AttendanceMRDto();
                attendanceMR.setEmployee(employee);
                attendanceMR.setAttendances(new ArrayList<AttendanceDto>());
                attendanceMR.setDate(new Date());
                amr.put(employee.getId(), attendanceMR);
            }
        else
            for (AttendanceMRDto attendancem : attendanceMRList) {
                amr.put(attendancem.getEmployee().getId(), attendancem);
            }

        for (EmployeeInfoDto employee : allEmployees) {
            AttendanceDto attendanced = new AttendanceDto().create();
            attendanced.setDate(new Date());
            attendanced.setDayNum(SalaryDateUtil.getDayNum(new Date()));
            attendanced.setEmployee(employee);
            attendanceDRList.add(attendanced);
        }
    }

    private static final long serialVersionUID = 1L;

    public void addAttendanceRecord() {

        for (AttendanceDto attendance : attendanceDRList) {
            // TODO
            AttendanceMRDto attendanceMRDto = amr.get(attendance.getEmployee().getId());
            boolean exist = attendanceMRDto.isExist(attendance);
            if (!exist)
                attendanceMRDto.addDAttendance(attendance);
        }
    }

    @Override
    protected boolean save(int saveFlags, String hint) {
        List<AttendanceMR> mrList = new ArrayList<AttendanceMR>();
        for (AttendanceDto day : attendanceDRList) {
            AttendanceMRDto month = amr.get(day.getEmployee().getId());

            /**
             * 如果该天的出勤记录已经存在，不去管
             */
            boolean exist = month.isExist(day);
            if (!exist) {
                day.setDate(openedDate);
                month.addDAttendance(day);
                mrList.add(month.unmarshal());
            }
        }

        try {
            DATA(AttendanceMR.class).saveOrUpdateAll(mrList);
            uiLogger.info("添加考勤记录成功");
        } catch (Exception e) {
            e.printStackTrace();
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
        return attendanceDRList;
    }

    public void setAttendanceDRList(List<AttendanceDto> attendanceDRList) {
        this.attendanceDRList = attendanceDRList;
    }
}
