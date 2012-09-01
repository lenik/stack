package com.bee32.sem.salary.dto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.process.base.ProcessEntityDto;
import com.bee32.sem.salary.entity.Salary;

public class SalaryDto
        extends ProcessEntityDto<Salary> {

    private static final long serialVersionUID = 1L;

    public static final int ELEMENTS = 1;

    int year;
    int month;
    EmployeeInfoDto employee;
    List<SalaryElementDto> elements;
    HashMap<Integer, BigDecimal> map;

    @Override
    protected void _marshal(Salary source) {
        year = source.getYear();
        month = source.getMonth();
        employee = mref(EmployeeInfoDto.class, source.getEmployee());

        if (selection.contains(ELEMENTS))
            elements = mrefList(SalaryElementDto.class, source.getElements());
        else
            elements = Collections.emptyList();

        if (selection.contains(ELEMENTS)) {
            map = new HashMap<Integer, BigDecimal>();
            for (SalaryElementDto element : elements)
                map.put(element.getDef().getOrder(), element.getBonus());
        }
    }

    @Override
    protected void _unmarshalTo(Salary target) {
        target.setYear(year);
        target.setMonth(month);
        merge(target, "employee", employee);
        target.setTotal(getTotal());

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

    public HashMap<Integer, BigDecimal> getMap() {
        if (map == null)
            return new HashMap<Integer, BigDecimal>();
        else
            return map;
    }

    public BigDecimal getTotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for (SalaryElementDto e : elements) {
            BigDecimal bonus = e.getBonus();
            sum = sum.add(bonus);
        }
        return sum;
    }

    public String getElementString() {
        StringBuffer sb = new StringBuffer();
        for (SalaryElementDto element : elements) {
            if (sb.length() != 0)
                sb.append(";");
            if (element.getDef().getCategory() != null) {
                sb.append(element.getDef().getCategory());
            }
            if (element.getDef().getLabel() != null) {
                sb.append("/");
                sb.append(element.getDef().getLabel());
            }
            if (element.getDef().getCategory() != null || element.getDef().getLabel() != null) {
                sb.append(":");
                sb.append(element.getBonus());
            }
        }
        return sb.toString();
    }
}
