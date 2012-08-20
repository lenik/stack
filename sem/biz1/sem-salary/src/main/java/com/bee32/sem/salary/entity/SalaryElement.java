package com.bee32.sem.salary.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;

/**
 * 工资条目
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "salary_element_seq", allocationSize = 1)
public class SalaryElement
        extends UIEntityAuto<Long>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    Salary parent;
    SalaryElementDef def;
    BigDecimal bonus = BigDecimal.ZERO;

    public SalaryElement() {
    }

    @ManyToOne(optional = false)
    public Salary getParent() {
        return parent;
    }

    public void setParent(Salary parent) {
        this.parent = parent;
    }

    /**
     * 工资元素的定义
     *
     * 如果这个元素无定义（没有对应的表达式， 则为 <code>null</code>。）
     */
    @ManyToOne
    @Cascade( {CascadeType.ALL})
    public SalaryElementDef getDef() {
        return def;
    }

    public void setDef(SalaryElementDef def) {
        this.def = def;
    }

    @Column(nullable = false, scale = MONEY_ITEM_SCALE, precision = MONEY_ITEM_PRECISION)
    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        if (bonus == null)
            throw new NullPointerException("bonus");
        this.bonus = bonus;
    }

}
