package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.EvaluationContext;

public class And
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final ICriteriaElement lhs;
    final ICriteriaElement rhs;

    public And(ICriteriaElement lhs, ICriteriaElement rhs) {
        if (lhs == null)
            throw new NullPointerException("lhs");
        if (rhs == null)
            throw new NullPointerException("rhs");
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public void apply(Criteria criteria) {
        lhs.apply(criteria);
        rhs.apply(criteria);
    }

    @Override
    protected Criterion buildCriterion() {
        Criterion l = lhs.getCriterion();
        Criterion r = rhs.getCriterion();
        if (l == null)
            return r;
        if (r == null)
            return l;
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
