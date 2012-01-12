package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class SizeNotEquals
        extends SizeCriteriaElement {

    private static final long serialVersionUID = 1L;

    public SizeNotEquals(String propertyName, int size) {
        super(propertyName, size);
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.sizeNe(propertyName, size);
    }

    @Override
    protected boolean filterSize(int sizeVar) {
        return sizeVar != size;
    }

    @Override
    public String getOperator() {
        return "!=";
    }

    @Override
    public String getOperatorName() {
        return "不等于";
    }

}
