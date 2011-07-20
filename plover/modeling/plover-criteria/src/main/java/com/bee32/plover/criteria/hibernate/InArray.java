package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class InArray
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;
    final Object[] values;

    public InArray(String propertyName, Object[] values) {
        this.propertyName = propertyName;
        this.values = values;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.in(propertyName, values);
    }

}
