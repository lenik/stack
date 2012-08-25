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
    BigDecimal bonus;

    @Override
    protected void _marshal(EventBonus source) {
        employee = mref(EmployeeInfoDto.class, source.getEmployee());
        bonus = source.getBonus();
    }

    @Override
    protected void _unmarshalTo(EventBonus target) {
        merge(target, "employee", employee);
        target.setBonus(bonus);
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

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
