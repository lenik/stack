package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class PropertyLessThan
        extends Property2CriteriaElement {

    private static final long serialVersionUID = 1L;

    public PropertyLessThan(String propertyName, String otherPropertyName) {
        super(propertyName, otherPropertyName);
    }

    @Override
    protected Criterion buildCriterion(int options) {
        return Restrictions.ltProperty(propertyName, otherPropertyName);
    }

    @Override
    protected boolean filterValue(Object lhs, Object rhs) {
        int cmp = CompareUtil.compare(lhs, rhs);
        return cmp < 0;
    }

    @Override
    protected String getOperator() {
        return "<";
    }

}
