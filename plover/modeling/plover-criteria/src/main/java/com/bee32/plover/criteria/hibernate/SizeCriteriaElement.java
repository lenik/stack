package com.bee32.plover.criteria.hibernate;

import java.util.Collection;

abstract class SizeCriteriaElement
        extends PropertyCriteriaElement {

    private static final long serialVersionUID = 1L;

    public SizeCriteriaElement(String propertyName) {
        super(propertyName);
    }

    @Override
    protected final boolean filterValue(Object val) {
        int size = 0;
        if (val instanceof Collection<?>) {
            Collection<?> coll = (Collection<?>) val;
            size = coll.size();
        }
        return filterSize(size);
    }

    protected abstract boolean filterSize(int sizeVar);

}
