package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class Equals
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;
    final Object value;

    public Equals(String propertyName, Object value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.eq(propertyName, value);
    }

}
