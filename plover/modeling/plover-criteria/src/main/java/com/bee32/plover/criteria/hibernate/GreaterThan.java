package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class GreaterThan
        extends CriteriaElement {

    final String propertyName;
    final Object value;

    public GreaterThan(String propertyName, Object value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.gt(propertyName, value);
    }

}
