package com.bee32.zebra.oa.salary;

import java.beans.Transient;

import net.bodz.bas.c.object.Nullables;

import com.tinylily.model.base.CoMomentInterval;

/**
 * 工资元素
 * 
 * 工资条上的元素（表达式）定义。
 * 
 * <p lang="en">
 * Salary Element Definition
 */
public class SalaryItemDef
        extends CoMomentInterval
        implements Comparable<SalaryItemDef> {

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
    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    @Override
    public int compareTo(SalaryItemDef o) {
        if (o == null)
            return -1;
        int cmp = this.order - o.order;
        if (cmp != 0)
            return cmp;

        cmp = Nullables.compare(getLabel(), o.getLabel());
        if (cmp != 0)
            return cmp;

        int id1 = System.identityHashCode(this);
        int id2 = System.identityHashCode(o);
        return id1 - id2;
    }

}
