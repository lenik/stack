package com.bee32.sem.wage.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.attendance.dto.AttendanceMRDto;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.wage.entity.BaseSalary;

public class BaseSalaryDto
        extends UIEntityDto<BaseSalary, Long> {

    private static final long serialVersionUID = 1L;

    Date date;
    AttendanceMRDto attendance;
    EmployeeInfoDto employee;
    List<BaseBonusDto> subsidies;
    BigDecimal total;

    @Override
    protected void _marshal(BaseSalary source) {
        date = source.getDate();
        attendance = mref(AttendanceMRDto.class, source.getAttendance());
        employee = mref(EmployeeInfoDto.class, source.getEmployee());
        subsidies = mrefList(BaseBonusDto.class, source.getSubsidies());
        total = source.getTotal();
    }

    @Override
    protected void _unmarshalTo(BaseSalary target) {
        target.setDate(date);
        merge(target, "attendance", attendance);
        merge(target, "employee", employee);
        mergeList(target, "subsidies", subsidies);
        target.setTotal(total);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
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

    public List<BaseBonusDto> getSubsidies() {
        return subsidies;
    }

    public void setSubsidies(List<BaseBonusDto> subsidies) {
        this.subsidies = subsidies;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
