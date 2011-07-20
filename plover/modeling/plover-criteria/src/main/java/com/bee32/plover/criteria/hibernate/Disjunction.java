package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class Disjunction
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    org.hibernate.criterion.Disjunction disjunction = Restrictions.disjunction();

    Disjunction() {
    }

    @Override
    protected Criterion buildCriterion() {
        return disjunction;
    }

    public Disjunction add(CriteriaElement element) {
        Criterion criterion = element.buildCriterion();
        disjunction.add(criterion);
        return this;
    }

}
