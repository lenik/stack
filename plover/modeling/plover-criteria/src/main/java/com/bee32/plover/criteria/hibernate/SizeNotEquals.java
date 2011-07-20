package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class SizeNotEquals
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;
    final int size;

    public SizeNotEquals(String propertyName, int size) {
        this.propertyName = propertyName;
        this.size = size;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.sizeNe(propertyName, size);
    }

}
