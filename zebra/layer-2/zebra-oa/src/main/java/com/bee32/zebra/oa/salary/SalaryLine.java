package com.bee32.zebra.oa.salary;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.bodz.lily.model.base.CoEntity;
import net.bodz.lily.model.base.IdType;

import com.bee32.zebra.oa.hr.Employee;

/**
 * 员工工资
 * 
 * 员工工资由若干个工资元素组成。
 */
@IdType(Integer.class)
public class SalaryLine
        extends CoEntity<Long> {

    private static final long serialVersionUID = 1L;

    int year;
    int month;
    Employee employee;

    List<SalaryItem> elements = new ArrayList<SalaryItem>();
    BigDecimal total = null; // BigDecimal.ZERO;

    boolean paid;

    public SalaryLine() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
    }

    /**
     * 年月
     * 
     * 年月的组合，如 201208。
     */
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

    /**
     * 员工
     * 
     * 工资对应的员工。
     */
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * 工资条目
     * 
     * 工资由若干个工资条目组成。
     */
    public List<SalaryItem> getElements() {
        return elements;
    }

    public void setElements(List<SalaryItem> elements) {
        if (elements == null)
            throw new NullPointerException("elements");
        this.elements = elements;
        this.total = null;
    }

    /**
     * 应发工资
     * 
     * 员工当月的应发工资。
     */
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

    /**
     * 已发工资项
     * 
     * 是否已经发工资
     */
    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    BigDecimal sum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (SalaryItem element : elements) {
            BigDecimal bonus = element.getBonus();
            sum = sum.add(bonus);
        }
        return sum;
    }

}
