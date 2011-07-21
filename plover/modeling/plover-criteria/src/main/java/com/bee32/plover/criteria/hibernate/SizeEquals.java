package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class SizeEquals extends CriteriaElement {

    final String propertyName;
    final int size;

    public SizeEquals(String propertyName, int size) {
        this.propertyName = propertyName;
        this.size = size;
    }
    @Override protected Criterion buildCriterion() {
        return Restrictions.sizeEq(propertyName, size);
    }

}
