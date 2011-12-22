package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;

public abstract class CriteriaElement
        implements ICriteriaElement {

    private static final long serialVersionUID = 1L;

    // @Override
    // public void apply(Criteria criteria) {
    // }

    @Override
    public final Criterion getCriterion() {
        return buildCriterion();
    }

    protected abstract Criterion buildCriterion();

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
