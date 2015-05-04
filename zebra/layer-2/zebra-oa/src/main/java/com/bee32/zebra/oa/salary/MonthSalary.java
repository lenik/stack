package com.bee32.zebra.oa.salary;

import java.beans.Transient;

import net.bodz.lily.model.base.CoEntity;
import net.bodz.lily.model.base.IdType;

import com.bee32.zebra.oa.accnt.AccountingEvent;

/**
 * 月度工资冗余
 * 
 * 所有员工某月的工资合计数. beginDate 代表月份
 * 
 * <p lang="en">
 * 
 */
@IdType(Integer.class)
public class MonthSalary
        extends CoEntity<Integer> {

    private static final long serialVersionUID = 1L;

    int year;
    int month;
    AccountingEvent ar;
    double value;

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
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * 月工资总额
     */
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
