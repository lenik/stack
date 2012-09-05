package com.bee32.sem.salary.dto;

import javax.free.ParseException;

import org.bouncycastle.util.Strings;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.salary.entity.SalaryElementDef;

public class SalaryElementDefDto
        extends MomentIntervalDto<SalaryElementDef> {

    private static final long serialVersionUID = 1L;

    String category;
    int order;
    String expr;

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    @Override
    protected void _marshal(SalaryElementDef source) {
        category = source.getCategory();
        order = source.getOrder();
        expr = source.getExpr();
    }

    @Override
    protected void _unmarshalTo(SalaryElementDef target) {
        target.setCategory(category);
        target.setOrder(order);
        target.setExpr(expr);
    }

    @NLength(max = SalaryElementDef.CATEGORY_LENGTH)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = TextUtil.normalizeSpace(category);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @NLength(min = 1, max = SalaryElementDef.EXPRESSION_LENGTH)
    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = TextUtil.normalizeSpace(expr);
    }

    public String getTitle() {
        if (getLabel() == null || getLabel().isEmpty())
            return category;
        else
            return category + "/" + getLabel();
    }

    public void setTitle(String title) {
        String[] split = Strings.split(title, '/');
        this.category = split[0];
        this.label = split[1];
    }
}
