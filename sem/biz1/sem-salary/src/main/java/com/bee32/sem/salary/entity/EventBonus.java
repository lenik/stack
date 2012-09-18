package com.bee32.sem.salary.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.sem.hr.entity.EmployeeInfo;

/**
 * 事件补贴
 *
 * 特殊事件的工资增加或减少，eg:工伤等。
 *
 * beginTime
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "event_bonus_seq", allocationSize = 1)
public class EventBonus
        extends MomentInterval {

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
    @ManyToOne
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
