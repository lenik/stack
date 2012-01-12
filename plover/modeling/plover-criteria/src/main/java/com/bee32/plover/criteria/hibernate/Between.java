package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class Between
        extends PropertyCriteriaElement {

    private static final long serialVersionUID = 1L;

    final Object lo;
    final Object hi;

    public Between(String propertyName, Object lo, Object hi) {
        super(propertyName);
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.between(propertyName, lo, hi);
    }

    @Override
    protected boolean filterValue(Object var) {
        // return true if: lo<=var<=hi.
        int loCmp = CompareUtil.compare(lo, var);
        if (loCmp < 0)
            return false;

        int hiCmp = CompareUtil.compare(var, hi);
        if (hiCmp > 0)
            return false;

        return true;
    }

    @Override
    public String getOperator() {
        return "BETWEEN";
    }

    @Override
    public String getOperatorName() {
        return "间于";
    }

    @Override
    protected void formatValue(StringBuilder out) {
        out.append(lo);
        out.append(" AND ");
        out.append(hi);
    }

}
