package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class NaturalId extends CriteriaElement {


    public NaturalId() {
    }
    @Override protected Criterion buildCriterion() {
        return Restrictions.naturalId();
    }

}
