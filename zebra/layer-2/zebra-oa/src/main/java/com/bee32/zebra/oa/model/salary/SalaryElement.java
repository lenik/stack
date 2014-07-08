package com.bee32.zebra.oa.model.salary;

import java.math.BigDecimal;

import com.tinylily.model.base.CoEntity;

/**
 * 员工工资条目
 *
 * 若干个工资条目组成员工工资。
 *
 * <p lang="en">
 * Salary Element
 */
public class SalaryElement
        extends CoEntity {

    private static final long serialVersionUID = 1L;

    Salary parent;
    SalaryElementDef def;
    BigDecimal bonus = BigDecimal.ZERO;

    /**
     * 父元素
     *
     * 工资条目对应的工资元素。
     */
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
    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        if (bonus == null)
            throw new NullPointerException("bonus");
        this.bonus = bonus;
    }

}
