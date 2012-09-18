package com.bee32.sem.salary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.MomentInterval;

/**
 * 工资元素
 *
 * 工资条上的元素（表达式）定义。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "salary_element_type_seq", allocationSize = 1)
public class SalaryElementDef
        extends MomentInterval
        implements Comparable<SalaryElementDef> {

    private static final long serialVersionUID = 1L;

    public static final int CATEGORY_LENGTH = 100;
    public static final int EXPRESSION_LENGTH = 200;

    String category;
    int order;
    String expr;
    boolean tax;

    /**
     * 工资字段分类
     *
     * 分类名称，大分类/小分类之间用 `/` 分隔。不包含本元素的名称。
     *
     * 如 `补贴`。
     */
    @Column(length = CATEGORY_LENGTH)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Transient
    public String getPath() {
        if (category == null || category.isEmpty())
            return getLabel();
        else
            return category + "/" + getLabel();
    }

    /**
     * 显示顺序
     *
     * 工资字段显示和计算次序。
     */
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * 表达式
     *
     * 工资字段的计算表达式。
     */
    @Column(nullable = false, length = EXPRESSION_LENGTH)
    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    @Override
    public int compareTo(SalaryElementDef o) {
        if (o == null)
            return -1;
        int cmp = this.order - o.order;
        if (cmp != 0)
            return cmp;

        if (label != o.label) {
            if (label == null)
                return -1;
            if (o.label == null)
                return 1;
            cmp = label.compareTo(o.label);
            if (cmp != 0)
                return cmp;
        }

        int id1 = System.identityHashCode(this);
        int id2 = System.identityHashCode(o);
        return id1 - id2;
    }

}
