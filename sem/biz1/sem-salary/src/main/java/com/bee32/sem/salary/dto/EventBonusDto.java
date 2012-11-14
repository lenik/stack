package com.bee32.sem.salary.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.salary.entity.EventBonus;

public class EventBonusDto
        extends MomentIntervalDto<EventBonus> {

    private static final long serialVersionUID = 1L;

    EmployeeInfoDto employee;
    BigDecimal value;

    @Override
    protected void _marshal(EventBonus source) {
        employee = mref(EmployeeInfoDto.class, source.getEmployee());
        value = source.getBonus();
    }

    @Override
    protected void _unmarshalTo(EventBonus target) {
        merge(target, "employee", employee);
        target.setBonus(value);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public EmployeeInfoDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeInfoDto employee) {
        this.employee = employee;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getTitle() {
        int compareTo = value.compareTo(BigDecimal.ZERO);
        if (compareTo > 0)
            return "奖";
        else
            return "惩";
    }

}
