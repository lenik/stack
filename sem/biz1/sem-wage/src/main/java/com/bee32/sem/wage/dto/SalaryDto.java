package com.bee32.sem.wage.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.attendance.dto.AttendanceMRDto;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.wage.entity.Salary;
import com.bee32.sem.wage.util.WageDateUtil;

public class SalaryDto
        extends UIEntityDto<Salary, Long> {

    private static final long serialVersionUID = 1L;

    Date date;
    AttendanceMRDto attendance;
    EmployeeInfoDto employee;
    WageSalaryItemDto baseSalary;
    List<WageSalaryItemDto> items;
    Map<String, WageSalaryItemDto> subsidies = new HashMap<String, WageSalaryItemDto>();
    BigDecimal total;

    @Override
    protected void _marshal(Salary source) {
        date = source.getDate();
        attendance = mref(AttendanceMRDto.class, source.getAttendance());
        employee = mref(EmployeeInfoDto.class, source.getEmployee());
        baseSalary = mref(WageSalaryItemDto.class, source.getBaseSalary());
        items = mrefList(WageSalaryItemDto.class, source.getItems());
        total = source.getTotal();
    }

    @Override
    protected void _unmarshalTo(Salary target) {
        target.setDate(date);
        merge(target, "attendance", attendance);
        merge(target, "employee", employee);
        merge(target, "baseSalary", baseSalary);
        mergeList(target, "items", items);
        target.setTotal(total);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public String getDateString() {
        if (date == null)
            date = new Date();
        return WageDateUtil.getDateString(date);
    }

    public String getSubsidyString() {
        StringBuffer sb = new StringBuffer("");
        for (WageSalaryItemDto item : items) {
            if (item.getBonus().doubleValue() != 0 && item.getRate() != 0) {
                if (sb.length() == 0)
                    sb.append(item.getLabel());
                else
                    sb.append("," + item.getLabel());
                sb.append(":");
                sb.append(item.getBonus().doubleValue() * item.getRate());
            }
        }
        return sb.toString();
    }

    public WageSalaryItemDto getItem(String alternate) {
        return subsidies.get(alternate);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AttendanceMRDto getAttendance() {
        return attendance;
    }

    public void setAttendance(AttendanceMRDto attendance) {
        this.attendance = attendance;
    }

    public EmployeeInfoDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeInfoDto employee) {
        this.employee = employee;
    }

    public WageSalaryItemDto getBaseSalary() {
        return baseSalary;
    }

    public List<WageSalaryItemDto> getItems() {
        return items;
    }

    public void setBaseSalary(WageSalaryItemDto baseSalary) {
        this.baseSalary = baseSalary;
    }

    public void setItems(List<WageSalaryItemDto> items) {
        this.items = items;
    }

    public Map<String, WageSalaryItemDto> getSubsidies() {
        return subsidies;
    }

    public void setSubsidies(Map<String, WageSalaryItemDto> subsidies) {
        this.subsidies = subsidies;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
