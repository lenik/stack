package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Subqueries;

public class SubqueryNotExists
        extends SubqueryElement {

    private static final long serialVersionUID = 1L;

    public SubqueryNotExists(String propertyName, Class<?> entityClass, ICriteriaElement subquery) {
        super(propertyName, entityClass, subquery);
    }

    @Override
    public String getOperator() {
        return "not exists";
    }

    @Override
    public String getOperatorName() {
        return "不存在";
    }

    @Override
    protected Criterion buildSubquery(DetachedCriteria dc) {
        return Subqueries.notExists(dc);
    }

}
