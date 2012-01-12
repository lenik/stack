package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class IsNotNull
        extends PropertyCriteriaElement {

    private static final long serialVersionUID = 1L;

    public IsNotNull(String propertyName) {
        super(propertyName);
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.isNotNull(propertyName);
    }

    @Override
    protected boolean filterValue(Object val) {
        return val != null;
    }

    @Override
    public String getOperator() {
        return "IS";
    }

    @Override
    public String getOperatorName() {
        return "不为";
    }

    @Override
    protected void formatValue(StringBuilder out) {
        out.append("NOT NULL");
    }

}
