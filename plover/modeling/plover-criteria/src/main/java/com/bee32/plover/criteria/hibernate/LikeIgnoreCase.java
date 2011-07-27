package com.bee32.plover.criteria.hibernate;

import javax.free.NotImplementedException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class LikeIgnoreCase
        extends PropertyCriteriaElement {

    private static final long serialVersionUID = 1L;

    final String value;
    final MatchMode matchMode;

    public LikeIgnoreCase(String propertyName, String value) {
        this(propertyName, value, null);
    }

    public LikeIgnoreCase(String propertyName, String value, MatchMode matchMode) {
        super(propertyName);
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

    @Override
    protected boolean filterValue(Object val) {
        throw new NotImplementedException("Like");
    }

}
