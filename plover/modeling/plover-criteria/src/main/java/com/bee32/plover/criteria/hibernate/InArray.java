package com.bee32.plover.criteria.hibernate;

import javax.free.Nullables;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class InArray
        extends PropertyCriteriaElement {

    private static final long serialVersionUID = 1L;

    final Object[] values;

    public InArray(String propertyName, Object[] values) {
        super(propertyName);
        this.values = values;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.in(propertyName, values);
    }

    @Override
    protected boolean filterValue(Object val) {
        for (Object item : values) {
            if (Nullables.equals(item, val))
                return true;
        }
        return false;
    }

}
