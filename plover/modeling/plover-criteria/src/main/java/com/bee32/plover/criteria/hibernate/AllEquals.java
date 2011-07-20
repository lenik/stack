package com.bee32.plover.criteria.hibernate;

import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class AllEquals
        extends CriteriaElement {

    final Map<?, ?> propertyNameValues;

    public AllEquals(Map<?, ?> propertyNameValues) {
        this.propertyNameValues = propertyNameValues;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.allEq(propertyNameValues);
    }

}
