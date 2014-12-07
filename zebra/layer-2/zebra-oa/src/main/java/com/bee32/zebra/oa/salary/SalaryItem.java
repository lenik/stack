package com.bee32.zebra.oa.salary;

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
public class SalaryItem
        extends CoEntity {

    private static final long serialVersionUID = 1L;

    long id;
    SalaryLine parent;
    SalaryItemDef def;
    BigDecimal bonus = BigDecimal.ZERO;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * 父元素
     * 
     * 工资条目对应的工资元素。
     */
    public SalaryLine getParent() {
        return parent;
    }

    public void setParent(SalaryLine parent) {
        this.parent = parent;
    }

    /**
     * 元素定义
     * 
     * 工资元素的标准定义。
     * 
     * 如果这个元素无定义（没有对应的表达式， 则为 <code>null</code>。）
     */
    public SalaryItemDef getDef() {
        return def;
    }

    public void setDef(SalaryItemDef def) {
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
