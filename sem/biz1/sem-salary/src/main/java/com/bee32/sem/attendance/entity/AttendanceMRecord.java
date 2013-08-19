package com.bee32.sem.attendance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.salary.util.SalaryDateUtil;

/**
 * 出勤表
 *
 * 记录员工一个月的出勤数据和一些其他信息，
 *
 * <p lang="en">
 * Attendance Mo/Record
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "salary_seq", allocationSize = 1)
public class AttendanceMRecord
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;
    public static final int ATTENDANCE_DATA_LENGTH = 500;

    int yearMonth;
    EmployeeInfo employee;
    boolean safe;
    String attendanceData;// eg: "1:NM,LE,HO;2:NM,NM,NM;"

    public AttendanceMRecord() {
        yearMonth = SalaryDateUtil.convertToYearMonth(new Date());
    }

    /**
     * 年月
     *
     * 出勤记录对应的年和月
     */
    public int getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(int yearMonth) {
        this.yearMonth = yearMonth;
    }

    /**
     * 雇员
     *
     * 出勤记录对应的人员
     */
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public EmployeeInfo getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeInfo employee) {
        this.employee = employee;
    }

    /**
     * 安全
     *
     * 本月是否发生安全事故
     */
    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    /**
     * 出勤数据
     *
     * 本月详细的出勤数据
     */
    @Column(length = ATTENDANCE_DATA_LENGTH)
    public String getAttendanceData() {
        return attendanceData;
    }

    public void setAttendanceData(String attendanceData) {
        this.attendanceData = attendanceData;
    }

}
