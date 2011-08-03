package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class LessOrEquals
        extends PropertyCriteriaElement {

    private static final long serialVersionUID = 1L;

    final Object value;

    public LessOrEquals(String propertyName, Object value) {
        super(propertyName);
        this.value = value;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.le(propertyName, value);
    }

    @Override
    protected boolean filterValue(Object var) {
        @SuppressWarnings("unchecked")
        Comparable<Object> _var = (Comparable<Object>) var;

        int cmp = _var.compareTo(value);

        return cmp <= 0;
    }

}
