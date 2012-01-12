package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class SizeLessOrEquals
        extends SizeCriteriaElement {

    private static final long serialVersionUID = 1L;

    public SizeLessOrEquals(String propertyName, int size) {
        super(propertyName, size);
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.sizeLe(propertyName, size);
    }

    @Override
    protected boolean filterSize(int sizeVar) {
        return sizeVar <= size;
    }

    @Override
    public String getOperator() {
        return "<=";
    }

    @Override
    public String getOperatorName() {
        return "小于等于";
    }

}
