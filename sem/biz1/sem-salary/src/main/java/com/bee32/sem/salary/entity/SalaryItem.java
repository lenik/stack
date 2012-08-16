package com.bee32.sem.salary.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 工资条目
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "salary_item_seq", allocationSize = 1)
public class SalaryItem
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Salary parent;
    SalaryElementType elementType;
    BigDecimal bonus;

    public SalaryItem() {
    }

    public SalaryElementType getElementType() {
        return elementType;
    }

    public void setElementType(SalaryElementType elementType) {
        this.elementType = elementType;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
