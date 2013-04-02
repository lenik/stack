package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Subqueries;

public class SubqueryPropertyNotIn
        extends SubqueryElement {

    private static final long serialVersionUID = 1L;

    public SubqueryPropertyNotIn(String propertyName, Class<?> entityClass, ICriteriaElement subquery) {
        super(propertyName, entityClass, subquery);
    }

    @Override
    public String getOperator() {
        return "property-not-in";
    }

    @Override
    public String getOperatorName() {
        return "属性不属于集合";
    }

    @Override
    protected Criterion buildSubquery(DetachedCriteria dc) {
        return Subqueries.propertyNotIn(propertyName, dc);
    }

}
