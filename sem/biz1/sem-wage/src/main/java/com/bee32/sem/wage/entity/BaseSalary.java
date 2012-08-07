package com.bee32.sem.wage.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.attendance.entity.AttendanceMR;
import com.bee32.sem.hr.entity.EmployeeInfo;

/**
 * 基础工资
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "salary_seq", allocationSize = 1)
public class BaseSalary
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Date date;

    /** 月出勤记录 */
    AttendanceMR attendance;
    /** 工资计算类型 */
    // AttendanceType type;
    /**
     * 冗余
     */
    EmployeeInfo employee;
    /** 奖惩 */
    List<BaseBonus> subsidies;

    BigDecimal total;

    @ManyToOne
    public AttendanceMR getAttendance() {
        return attendance;
    }

    public void setAttendance(AttendanceMR attendance) {
        this.attendance = attendance;
    }

    /**
     * 工资条对应月份
     */
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne
    public EmployeeInfo getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeInfo employee) {
        this.employee = employee;
    }

    @OneToMany
    public List<BaseBonus> getSubsidies() {
        return subsidies;
    }

    public void setSubsidies(List<BaseBonus> subsidies) {
        this.subsidies = subsidies;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
