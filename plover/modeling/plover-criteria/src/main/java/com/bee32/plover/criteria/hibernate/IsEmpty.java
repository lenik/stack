package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class IsEmpty extends CriteriaElement {

    final String propertyName;

    public IsEmpty(String propertyName) {
        this.propertyName = propertyName;
    }
    @Override protected Criterion buildCriterion() {
        return Restrictions.isEmpty(propertyName);
    }

}
