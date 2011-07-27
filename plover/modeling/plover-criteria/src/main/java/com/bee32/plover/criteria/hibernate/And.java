package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.EvaluationContext;

public class And
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final CriteriaElement lhs;
    final CriteriaElement rhs;

    public And(CriteriaElement lhs, CriteriaElement rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    protected Criterion buildCriterion() {
        Criterion l = lhs.buildCriterion();
        Criterion r = rhs.buildCriterion();
        return Restrictions.and(l, r);
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        if (!lhs.filter(obj, context))
            return false;

        if (!rhs.filter(obj, context))
            return false;

        return true;
    }

}
