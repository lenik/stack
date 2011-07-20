package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class PropertyLessThan
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;
    final String otherPropertyName;

    public PropertyLessThan(String propertyName, String otherPropertyName) {
        this.propertyName = propertyName;
        this.otherPropertyName = otherPropertyName;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.ltProperty(propertyName, otherPropertyName);
    }

}
