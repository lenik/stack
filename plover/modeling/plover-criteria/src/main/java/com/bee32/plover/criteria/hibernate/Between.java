package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class Between
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;
    final Object lo;
    final Object hi;

    public Between(String propertyName, Object lo, Object hi) {
        this.propertyName = propertyName;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.between(propertyName, lo, hi);
    }

}
