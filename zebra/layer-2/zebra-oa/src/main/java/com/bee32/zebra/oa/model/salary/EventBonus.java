package com.bee32.zebra.oa.model.salary;

import java.math.BigDecimal;

import com.bee32.zebra.oa.model.hr.EmployeeInfo;
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

    EmployeeInfo employee;
    BigDecimal bonus;

    public EventBonus() {
    }

    /**
     * 员工
     *
     * 特殊事件对应的员工。
     */
    public EmployeeInfo getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeInfo employee) {
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
