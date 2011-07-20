package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

public abstract class CriteriaElement
        implements ICriteriaElement {

    @Override
    public void apply(Criteria criteria) {
        Criterion criterion = buildCriterion();
        if (criterion != null)
            criteria.add(criterion);
    }

    protected abstract Criterion buildCriterion();

}
