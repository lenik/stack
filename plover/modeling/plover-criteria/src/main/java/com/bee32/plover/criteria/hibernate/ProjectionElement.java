package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.springframework.expression.EvaluationContext;

public abstract class ProjectionElement
        extends AbstractCriteriaElement {

    private static final long serialVersionUID = 1L;

    @Override
    public void apply(Criteria criteria, int options) {
        if ((options & NO_PROJECTION) != 0)
            return;
        Projection projection = buildProjection();
        criteria.setProjection(projection);
    }

    @Override
    public Criterion getCriterion(int options) {
        return null;
    }

    protected abstract Projection buildProjection();

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        // Do nothing in projection elements.
        return true;
    }

}
