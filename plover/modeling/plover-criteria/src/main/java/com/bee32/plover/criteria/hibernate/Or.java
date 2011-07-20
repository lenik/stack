package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class Or
        extends CriteriaElement {

    final CriteriaElement lhs;
    final CriteriaElement rhs;

    public Or(CriteriaElement lhs, CriteriaElement rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    protected Criterion buildCriterion() {
        Criterion l = lhs.buildCriterion();
        Criterion r = rhs.buildCriterion();
        return Restrictions.or(l, r);
    }

}
