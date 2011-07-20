package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class Conjunction
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    org.hibernate.criterion.Conjunction conjunction = Restrictions.conjunction();

    Conjunction() {
    }

    @Override
    protected Criterion buildCriterion() {
        return conjunction;
    }

    public Conjunction add(CriteriaElement element) {
        Criterion criterion = element.buildCriterion();
        conjunction.add(criterion);
        return this;
    }

}
