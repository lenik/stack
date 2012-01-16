package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

public abstract class AbstractCriteriaElement
        implements ICriteriaElement {

    private static final long serialVersionUID = 1L;

    @Override
    public final void apply(Criteria criteria) {
        apply(criteria, 0);
    }

    @Override
    public final Criterion getCriterion() {
        return getCriterion(0);
    }

}
