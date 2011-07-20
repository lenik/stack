package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class IdEquals extends CriteriaElement {

    final Object value;

    public IdEquals(Object value) {
        this.value = value;
    }
    @Override protected Criterion buildCriterion() {
        return Restrictions.idEq(value);
    }

}
