package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class SizeGreaterThan
        extends SizeCriteriaElement {

    private static final long serialVersionUID = 1L;

    final int size;

    public SizeGreaterThan(String propertyName, int size) {
        super(propertyName);
        this.size = size;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.sizeGt(propertyName, size);
    }

    @Override
    protected boolean filterSize(int sizeVar) {
        return sizeVar > size;
    }

}
