package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class Disjunction extends CriteriaElement {


    public Disjunction() {
    }
    @Override protected Criterion buildCriterion() {
        return Restrictions.disjunction();
    }

}
