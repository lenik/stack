package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class IdEquals
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final Object value;

    public IdEquals(Object value) {
        this.value = value;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.idEq(value);
    }

}
