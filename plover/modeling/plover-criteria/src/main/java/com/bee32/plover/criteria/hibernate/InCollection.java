package com.bee32.plover.criteria.hibernate;

import java.util.Collection;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class InCollection
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;
    final Collection<?> values;

    public InCollection(String propertyName, Collection<?> values) {
        this.propertyName = propertyName;
        this.values = values;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.in(propertyName, values);
    }

}
