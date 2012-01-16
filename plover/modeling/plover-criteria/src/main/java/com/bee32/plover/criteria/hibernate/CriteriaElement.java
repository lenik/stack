package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;

public abstract class CriteriaElement
        extends AbstractCriteriaElement {

    private static final long serialVersionUID = 1L;

    // @Override
    // public void apply(Criteria criteria) {
    // }

    @Override
    public final Criterion getCriterion(int options) {
        return buildCriterion(options);
    }

    protected abstract Criterion buildCriterion(int options);

    public final boolean filter(Object obj) {
        return filter(obj, null);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        format(buffer);
        return buffer.toString();
    }

}
