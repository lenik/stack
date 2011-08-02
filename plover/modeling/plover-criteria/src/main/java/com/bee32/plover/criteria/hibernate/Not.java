package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.EvaluationContext;

public class Not
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final ICriteriaElement expression;

    public Not(ICriteriaElement expression) {
        this.expression = expression;
    }

    @Override
    public void apply(Criteria criteria) {
        expression.apply(criteria);
    }

    @Override
    protected Criterion buildCriterion() {
        Criterion expr = expression.getCriterion();
        if (expr == null)
            return null;
        return Restrictions.not(expr);
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        return !expression.filter(obj, context);
    }

}
