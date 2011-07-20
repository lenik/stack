package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class PropertyGreaterThan extends CriteriaElement {

    final String propertyName;
    final String otherPropertyName;

    public PropertyGreaterThan(String propertyName, String otherPropertyName) {
        this.propertyName = propertyName;
        this.otherPropertyName = otherPropertyName;
    }
    @Override protected Criterion buildCriterion() {
        return Restrictions.gtProperty(propertyName, otherPropertyName);
    }

}
