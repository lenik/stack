package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.EvaluationContext;

public class Or
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final ICriteriaElement lhs;
    final ICriteriaElement rhs;

    Or(ICriteriaElement lhs, ICriteriaElement rhs) {
        if (lhs == null)
            throw new NullPointerException("lhs");
        if (rhs == null)
            throw new NullPointerException("rhs");
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public static ICriteriaElement of(ICriteriaElement lhs, ICriteriaElement rhs) {
        if (lhs == null)
            return rhs;
        if (rhs == null)
            return lhs;
        return new Or(lhs, rhs);
    }

    public static CriteriaElement of(CriteriaElement lhs, CriteriaElement rhs) {
        if (lhs == null)
            return null;
        if (rhs == null)
            return null;
        return new Or(lhs, rhs);
    }

    @Override
    public void apply(Criteria criteria, int options) {
        if (lhs != null)
            lhs.apply(criteria, options);
        if (rhs != null)
            rhs.apply(criteria, options);
    }

    @Override
    protected Criterion buildCriterion(int options) {
        Criterion l = lhs.getCriterion(options);
        Criterion r = rhs.getCriterion(options);
        if (l == null)
            return r;
        if (r == null)
            return l;
        return Restrictions.or(l, r);
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        if (lhs.filter(obj, context))
            return true;

        if (rhs.filter(obj, context))
            return true;

        return false;
    }

    @Override
    public void format(StringBuilder out) {
        out.append("(or ");
        lhs.format(out);
        out.append(" ");
        rhs.format(out);
        out.append(")");
    }

}
