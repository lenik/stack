package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class SizeGreaterOrEquals
        extends SizeCriteriaElement {

    private static final long serialVersionUID = 1L;

    final int size;

    public SizeGreaterOrEquals(String propertyName, int size) {
        super(propertyName);
        this.size = size;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.sizeGe(propertyName, size);
    }

    @Override
    protected boolean filterSize(int sizeVar) {
        return sizeVar >= size;
    }

}
