package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

public abstract class SubqueryElement
        extends PropertyCriteriaElement {

    private static final long serialVersionUID = 1L;

    Class<?> entityClass;
    ICriteriaElement subquery;

    public SubqueryElement(String propertyName, Class<?> entityClass, ICriteriaElement subquery) {
        super(propertyName);
        if (entityClass == null)
            throw new NullPointerException("entityClass");
        if (subquery == null)
            throw new NullPointerException("subquery");
        this.entityClass = entityClass;
        this.subquery = subquery;
    }

    @Override
    protected void formatValue(StringBuilder out) {
        out.append(subquery.toString());
    }

    @Override
    protected boolean filterValue(Object var) {
        // return subquery.isEmpty();
        return false;
    }

    @Override
    protected Criterion buildCriterion(int options) {
        DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
        Criterion criterion = subquery.getCriterion();
        dc.add(criterion);
        return buildSubquery(dc);
    }

    protected abstract Criterion buildSubquery(DetachedCriteria dc);

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public ICriteriaElement getSubquery() {
        return subquery;
    }

}
