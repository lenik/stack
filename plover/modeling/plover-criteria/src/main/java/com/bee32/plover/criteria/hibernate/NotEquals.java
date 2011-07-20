package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class NotEquals
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;
    final Object value;

    public NotEquals(String propertyName, Object value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.ne(propertyName, value);
    }

}
