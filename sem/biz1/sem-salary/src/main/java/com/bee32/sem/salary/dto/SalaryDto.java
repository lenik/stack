package com.bee32.sem.salary.dto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.salary.entity.Salary;

public class SalaryDto
        extends UIEntityDto<Salary, Long> {

    private static final long serialVersionUID = 1L;

    public static final int ELEMENTS = 1;

    int year;
    int month;
    EmployeeInfoDto employee;
    List<SalaryElementDto> elements;
    BigDecimal total;

    @Override
    protected void _marshal(Salary source) {
        year = source.getYear();
        month = source.getMonth();
        employee = mref(EmployeeInfoDto.class, source.getEmployee());
        total = source.getTotal();

        if (selection.contains(ELEMENTS))
            elements = mrefList(SalaryElementDto.class, source.getElements());
        else
            elements = Collections.emptyList();
    }

    @Override
    protected void _unmarshalTo(Salary target) {
        target.setYear(year);
        target.setMonth(month);
        merge(target, "employee", employee);
        target.setTotal(total);

        if (selection.contains(ELEMENTS))
            mergeList(target, "elements", elements);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public String getDateString() {
        return year + "年" + month + "月";
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

    public EmployeeInfoDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeInfoDto employee) {
        this.employee = employee;
    }

    public List<SalaryElementDto> getElements() {
        return elements;
    }

    public void setElements(List<SalaryElementDto> elements) {
        this.elements = elements;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
