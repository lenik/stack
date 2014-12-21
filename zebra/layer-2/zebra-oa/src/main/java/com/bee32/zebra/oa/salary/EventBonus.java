package com.bee32.zebra.oa.salary;

import java.math.BigDecimal;

import com.bee32.zebra.oa.hr.Employee;
import com.tinylily.model.base.CoMomentInterval;

/**
 * 事件补贴
 * 
 * 特殊事件的工资增加或减少，eg:工伤等。
 * 
 * beginTime
 * 
 * <p lang="en">
 * Event Bonus
 */
public class EventBonus
        extends CoMomentInterval {

    private static final long serialVersionUID = 1L;

    int id;
    Employee employee;
    BigDecimal bonus;

    public EventBonus() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 员工
     * 
     * 特殊事件对应的员工。
     */
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * 事件金额
     * 
     * 特殊事件补贴的金额，可奖可惩。
     */
    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
