package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Subqueries;

public class SubqueryExists
        extends SubqueryElement {

    private static final long serialVersionUID = 1L;

    public SubqueryExists(String propertyName, Class<?> entityClass, ICriteriaElement subquery) {
        super(propertyName, entityClass, subquery);
    }

    @Override
    public String getOperator() {
        return "exists";
    }

    @Override
    public String getOperatorName() {
        return "存在";
    }

    @Override
    protected Criterion buildSubquery(DetachedCriteria dc) {
        return Subqueries.exists(dc);
    }

}
