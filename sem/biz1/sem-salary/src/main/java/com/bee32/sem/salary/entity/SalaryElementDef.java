package com.bee32.sem.salary.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.MomentInterval;

/**
 * 工资条上的元素（表达式）定义
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "salary_element_type_seq", allocationSize = 1)
public class SalaryElementDef
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

    String category;
    int order;
    String expr;
    boolean tax;

    /**
     * 分类名称，大分类/小分类之间用 `/` 分隔。不包含本元素的名称
     *
     * 如 `补贴`。
     */
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 显示和计算次序
     */
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * 表达式
     */
    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    /**
     * 是否税前工资
     */
    public boolean isTax() {
        return tax;
    }

    public void setTax(boolean tax) {
        this.tax = tax;
    }

}
