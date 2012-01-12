package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class IsNull
        extends PropertyCriteriaElement {

    private static final long serialVersionUID = 1L;

    public IsNull(String propertyName) {
        super(propertyName);
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.isNull(propertyName);
    }

    @Override
    protected boolean filterValue(Object val) {
        return val == null;
    }

    @Override
    public String getOperator() {
        return "IS";
    }

    @Override
    public String getOperatorName() {
        return "为";
    }

    @Override
    protected void formatValue(StringBuilder out) {
        out.append("NULL");
    }

}
