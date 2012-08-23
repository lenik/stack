package com.bee32.sem.salary.dto;

import javax.free.ParseException;

import org.bouncycastle.util.Strings;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.salary.entity.SalaryElementDef;

public class SalaryElementDefDto
        extends UIEntityDto<SalaryElementDef, Integer> {

    private static final long serialVersionUID = 1L;

    String category;
    int order;
    String expr;
    boolean tax;

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    @Override
    protected void _marshal(SalaryElementDef source) {
        category = source.getCategory();
        order = source.getOrder();
        expr = source.getExpr();
        tax = source.isTax();
    }

    @Override
    protected void _unmarshalTo(SalaryElementDef target) {
        target.setCategory(category);
        target.setOrder(order);
        target.setExpr(expr);
        target.setTax(tax);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public boolean isTax() {
        return tax;
    }

    public void setTax(boolean tax) {
        this.tax = tax;
    }

    public String getTitle() {
        if (getLabel() == null || getLabel().isEmpty())
            return category;
        else
            return category + "/" + getLabel();
    }

    public void setTitle(String title){
        String[] split = Strings.split(title, '/');
        this.category = split[0];
        this.label = split[1];
    }
}
