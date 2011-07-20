package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class PropertyNotEquals
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;
    final String otherPropertyName;

    public PropertyNotEquals(String propertyName, String otherPropertyName) {
        this.propertyName = propertyName;
        this.otherPropertyName = otherPropertyName;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.neProperty(propertyName, otherPropertyName);
    }

}
