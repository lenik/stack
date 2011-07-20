package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class Not extends CriteriaElement {

    final Criterion expression;

    public Not(Criterion expression) {
        this.expression = expression;
    }
    @Override protected Criterion buildCriterion() {
        return Restrictions.not(expression);
    }

}
