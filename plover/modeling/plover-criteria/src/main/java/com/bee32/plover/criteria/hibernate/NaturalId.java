package com.bee32.plover.criteria.hibernate;

import javax.free.NotImplementedException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.EvaluationContext;

public class NaturalId
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    public NaturalId() {
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.naturalId();
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        // TODO
        throw new NotImplementedException();
    }

}
