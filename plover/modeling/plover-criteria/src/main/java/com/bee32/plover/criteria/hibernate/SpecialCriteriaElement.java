package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.springframework.expression.EvaluationContext;

public abstract class SpecialCriteriaElement
        extends AbstractCriteriaElement {

    private static final long serialVersionUID = 1L;

    @Override
    public Criterion getCriterion(int options) {
        return null;
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        return true;
    }

}
