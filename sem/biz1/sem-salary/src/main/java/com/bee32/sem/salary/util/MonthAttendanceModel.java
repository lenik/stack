package com.bee32.sem.salary.util;

import java.io.Serializable;
import java.util.List;

import com.bee32.sem.attendance.dto.AttendanceDto;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.dto.JobPerformanceDto;

public class MonthAttendanceModel
        implements Serializable {

    private static final long serialVersionUID = 1L;

    EmployeeInfoDto employee;
    List<AttendanceDto> attendances;
    JobPerformanceDto performance;

    public EmployeeInfoDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeInfoDto employee) {
        this.employee = employee;
    }

    public List<AttendanceDto> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<AttendanceDto> attendances) {
        this.attendances = attendances;
    }

    public JobPerformanceDto getPerformance() {
        return performance;
    }

    public void setPerformance(JobPerformanceDto performance) {
        this.performance = performance;
    }

}
