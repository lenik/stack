package com.bee32.plover.criteria.hibernate;

import java.util.Collection;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class IsEmpty
        extends PropertyCriteriaElement {

    private static final long serialVersionUID = 1L;

    public IsEmpty(String propertyName) {
        super(propertyName);
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.isEmpty(propertyName);
    }

    @Override
    protected boolean filterValue(Object val) {
        if (val instanceof Collection<?>) {
            Collection<?> col = (Collection<?>) val;
            return col.isEmpty();
        }
        // return false;
        throw new IllegalArgumentException("is-not-empty testing on non-collection type " + val.getClass());
    }

}
