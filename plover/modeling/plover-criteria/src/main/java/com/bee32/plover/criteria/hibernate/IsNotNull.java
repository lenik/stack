package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class IsNotNull
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;

    public IsNotNull(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.isNotNull(propertyName);
    }

}
