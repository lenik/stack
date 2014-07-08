package com.bee32.zebra.oa.model.salary;

import java.beans.Transient;

import com.bee32.zebra.oa.model.accnt.AccountRecord;
import com.tinylily.model.base.CoEntity;

/**
 * 月度工资冗余
 *
 * 所有员工某月的工资合计数. beginTime代表月份
 *
 * <p lang="en">
 *
 */
public class MonthSalary
        extends CoEntity {

    private static final long serialVersionUID = 1L;

    int year;
    int month;
    AccountRecord ar;
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
