package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class IsNull extends CriteriaElement {

    final String propertyName;

    public IsNull(String propertyName) {
        this.propertyName = propertyName;
    }
    @Override protected Criterion buildCriterion() {
        return Restrictions.isNull(propertyName);
    }

}
