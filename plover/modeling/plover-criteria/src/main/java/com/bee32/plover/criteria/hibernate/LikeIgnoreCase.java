package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

class LikeIgnoreCase
        extends CriteriaElement {

    final String propertyName;
    final String value;
    final MatchMode matchMode;

    public LikeIgnoreCase(String propertyName, String value) {
        this(propertyName, value, null);
    }

    public LikeIgnoreCase(String propertyName, String value, MatchMode matchMode) {
        this.propertyName = propertyName;
        this.value = value;
        this.matchMode = matchMode;
    }

    @Override
    protected Criterion buildCriterion() {
        if (matchMode == null)
            return Restrictions.ilike(propertyName, value);
        else
            return Restrictions.ilike(propertyName, value, matchMode);
    }

}
