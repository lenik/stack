package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.EvaluationContext;

public class Not
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final ICriteriaElement expression;

    Not(ICriteriaElement expression) {
        if (expression == null)
            throw new NullPointerException("expression");
        this.expression = expression;
    }

    public static ICriteriaElement of(ICriteriaElement expression) {
        if (expression == null)
            return null;
        return new Not(expression);
    }

    public static CriteriaElement of(CriteriaElement expression) {
        if (expression == null)
            return null;
        return new Not(expression);
    }

    @Override
    public void apply(Criteria criteria, int options) {
        if (expression != null)
            expression.apply(criteria, options);
    }

    @Override
    protected Criterion buildCriterion(int options) {
        Criterion expr = expression.getCriterion(options);
        if (expr == null)
            return null;
        return Restrictions.not(expr);
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        return !expression.filter(obj, context);
    }

    @Override
    public void format(StringBuilder out) {
        out.append("(not ");
        expression.format(out);
        out.append(")");
    }

}
