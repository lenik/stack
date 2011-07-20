package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class IsNull
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;

    public IsNull(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.isNull(propertyName);
    }

}
