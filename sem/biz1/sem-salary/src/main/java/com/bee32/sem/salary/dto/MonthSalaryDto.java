package com.bee32.sem.salary.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.salary.entity.MonthSalary;

public class MonthSalaryDto
        extends EntityDto<MonthSalary, Long> {

    private static final long serialVersionUID = 1L;

    int year;
    int month;
    BigDecimal value;

    @Override
    protected void _marshal(MonthSalary source) {
        year = source.getYear();
        month = source.getMonth();
        value = source.getValue();
    }

    @Override
    protected void _unmarshalTo(MonthSalary target) {
        target.setYear(year);
        target.setMonth(month);
        target.setValue(value);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
