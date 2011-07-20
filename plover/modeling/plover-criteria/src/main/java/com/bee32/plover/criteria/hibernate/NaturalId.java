package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class NaturalId
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    public NaturalId() {
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.naturalId();
    }

}
