package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projection;
import org.springframework.expression.EvaluationContext;

public abstract class ProjectionElement
        implements ICriteriaElement {

    private static final long serialVersionUID = 1L;

    @Override
    public void apply(Criteria criteria) {
        Projection projection = buildProjection();
        criteria.setProjection(projection);
    }

    protected abstract Projection buildProjection();

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        // Do nothing in projection elements.
        return true;
    }

}
