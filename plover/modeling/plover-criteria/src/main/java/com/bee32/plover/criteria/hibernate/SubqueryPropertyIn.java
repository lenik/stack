package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Subqueries;

public class SubqueryPropertyIn
        extends SubqueryElement {

    private static final long serialVersionUID = 1L;

    public SubqueryPropertyIn(String propertyName, Class<?> entityClass, ICriteriaElement subquery) {
        super(propertyName, entityClass, subquery);
    }

    @Override
    public String getOperator() {
        return "property-in";
    }

    @Override
    public String getOperatorName() {
        return "属性属于集合";
    }

    @Override
    protected Criterion buildSubquery(DetachedCriteria dc) {
        return Subqueries.propertyIn(propertyName, dc);
    }

}
