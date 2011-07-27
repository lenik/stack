package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class GreaterOrEquals
        extends PropertyCriteriaElement {

    private static final long serialVersionUID = 1L;

    final Object value;

    public GreaterOrEquals(String propertyName, Object value) {
        super(propertyName);
        this.value = value;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.ge(propertyName, value);
    }

    @Override
    protected boolean filterValue(Object val) {
        @SuppressWarnings("unchecked")
        Comparable<Object> lhs = (Comparable<Object>) value;

        int cmp = lhs.compareTo(value);

        return cmp >= 0;
    }

}
