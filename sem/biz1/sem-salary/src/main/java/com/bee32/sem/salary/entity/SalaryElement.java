package com.bee32.sem.salary.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;

/**
 * 员工工资条目
 *
 * 若干个工资条目组成员工工资。
 *
 * <p lang="en">
 * Salary Element
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

    /**
     * 父元素
     *
     * 工资条目对应的工资元素。
     */
    @ManyToOne(optional = false)
    public Salary getParent() {
        return parent;
    }

    public void setParent(Salary parent) {
        this.parent = parent;
    }

    /**
     * 元素定义
     *
     * 工资元素的标准定义。
     *
     * 如果这个元素无定义（没有对应的表达式， 则为 <code>null</code>。）
     */
    @ManyToOne
    public SalaryElementDef getDef() {
        return def;
    }

    public void setDef(SalaryElementDef def) {
        this.def = def;
    }

    /**
     * 元素工资
     *
     * 元素对应某个员工的工资。
     */
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
