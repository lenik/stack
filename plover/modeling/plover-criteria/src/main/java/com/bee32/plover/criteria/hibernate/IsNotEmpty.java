package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class IsNotEmpty extends CriteriaElement {

    final String propertyName;

    public IsNotEmpty(String propertyName) {
        this.propertyName = propertyName;
    }
    @Override protected Criterion buildCriterion() {
        return Restrictions.isNotEmpty(propertyName);
    }

}
