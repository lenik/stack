package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class Not
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final CriteriaElement expression;

    public Not(CriteriaElement expression) {
        this.expression = expression;
    }

    @Override
    protected Criterion buildCriterion() {
        Criterion expr = expression.buildCriterion();
        return Restrictions.not(expr);
    }

}
