package com.bee32.plover.criteria.hibernate;

import javax.free.Nullables;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class NotEquals
        extends PropertyCriteriaElement {

    private static final long serialVersionUID = 1L;

    final Object value;

    public NotEquals(String propertyName, Object value) {
        super(propertyName);
        this.value = value;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.ne(propertyName, value);
    }

    @Override
    protected boolean filterValue(Object val) {
        return !Nullables.equals(val, value);
    }

}
