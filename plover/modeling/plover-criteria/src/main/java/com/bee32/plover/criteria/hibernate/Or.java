package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class Or extends CriteriaElement {

    final Criterion lhs;
    final Criterion rhs;

    public Or(Criterion lhs, Criterion rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
    @Override protected Criterion buildCriterion() {
        return Restrictions.or(lhs, rhs);
    }

}
