package com.bee32.sem.salary.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.hr.entity.EmployeeInfo;

/**
 * 基础工资
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "salary_seq", allocationSize = 1)
public class Salary
        extends UIEntityAuto<Long>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    int year;
    int month;
    EmployeeInfo employee;

    List<SalaryElement> elements = new ArrayList<SalaryElement>();
    BigDecimal total = null; // BigDecimal.ZERO;

    public Salary() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
    }

    /**
     * 年月的组合，如 201208。
     */
    @Column(nullable = false)
    public int getYearMonth() {
        return year * 100 + month;
    }

    public void setYearMonth(int yearMonth) {
        year = yearMonth / 100;
        month = yearMonth % 100;
    }

    /**
     * 工资条对应年
     */
    @Transient
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * 工资条对应月份
     */
    @Transient
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @ManyToOne
    public EmployeeInfo getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeInfo employee) {
        this.employee = employee;
    }

    @OneToMany(mappedBy = "parent")
    @Cascade({ CascadeType.ALL })
    public List<SalaryElement> getElements() {
        return elements;
    }

    public void setElements(List<SalaryElement> elements) {
        if (elements == null)
            throw new NullPointerException("elements");
        this.elements = elements;
        this.total = null;
    }

    @Column(nullable = false, scale = MONEY_TOTAL_SCALE, precision = MONEY_TOTAL_PRECISION)
    public BigDecimal getTotal() {
        if (total == null) {
            synchronized (this) {
                if (total == null) {
                    total = sum();
                }
            }
        }
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    BigDecimal sum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (SalaryElement element : elements) {
            BigDecimal bonus = element.getBonus();
            sum = sum.add(bonus);
        }
        return sum;
    }

}
