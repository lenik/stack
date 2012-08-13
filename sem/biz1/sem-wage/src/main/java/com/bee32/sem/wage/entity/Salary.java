package com.bee32.sem.wage.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.attendance.entity.AttendanceMR;
import com.bee32.sem.hr.entity.EmployeeInfo;

/**
 * 基础工资
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "salary_seq", allocationSize = 1)
public class Salary
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Date date;

    /** 月出勤记录 */
    AttendanceMR attendance;
    /** 工资计算类型 */
    // AttendanceType type;

    EmployeeInfo employee;

    WageSalaryItem baseSalary;

    List<WageSalaryItem> items;

    /**
     * 冗余
     */
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
    @Cascade({ CascadeType.ALL })
    public List<WageSalaryItem> getItems() {
        return items;
    }

    public void setItems(List<WageSalaryItem> items) {
        this.items = items;
    }

    @OneToOne
    @Cascade({ CascadeType.ALL })
    public WageSalaryItem getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(WageSalaryItem baseSalary) {
        this.baseSalary = baseSalary;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
