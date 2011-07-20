package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class IsNotNull extends CriteriaElement {

    final String propertyName;

    public IsNotNull(String propertyName) {
        this.propertyName = propertyName;
    }
    @Override protected Criterion buildCriterion() {
        return Restrictions.isNotNull(propertyName);
    }

}
