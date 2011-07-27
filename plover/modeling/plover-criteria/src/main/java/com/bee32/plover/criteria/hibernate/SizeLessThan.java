package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class SizeLessThan
        extends SizeCriteriaElement {

    private static final long serialVersionUID = 1L;

    final int size;

    public SizeLessThan(String propertyName, int size) {
        super(propertyName);
        this.size = size;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.sizeLt(propertyName, size);
    }

    @Override
    protected boolean filterSize(int sizeVar) {
        return sizeVar < size;
    }

}
