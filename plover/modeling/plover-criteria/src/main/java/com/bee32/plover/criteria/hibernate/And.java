package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class And extends CriteriaElement {

    final Criterion lhs;
    final Criterion rhs;

    public And(Criterion lhs, Criterion rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
    @Override protected Criterion buildCriterion() {
        return Restrictions.and(lhs, rhs);
    }

}
