package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class Conjunction extends CriteriaElement {


    public Conjunction() {
    }
    @Override protected Criterion buildCriterion() {
        return Restrictions.conjunction();
    }

}
