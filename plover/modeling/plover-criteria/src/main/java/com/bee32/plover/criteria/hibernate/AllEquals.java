package com.bee32.plover.criteria.hibernate;

import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class AllEquals
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final Map<?, ?> propertyNameValues;

    public AllEquals(Map<?, ?> propertyNameValues) {
        this.propertyNameValues = propertyNameValues;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.allEq(propertyNameValues);
    }

}
