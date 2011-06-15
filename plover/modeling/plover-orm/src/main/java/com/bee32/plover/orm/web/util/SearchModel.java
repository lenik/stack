package com.bee32.plover.orm.web.util;

import org.hibernate.criterion.DetachedCriteria;

import com.bee32.plover.orm.entity.Entity;

public class SearchModel
        extends DecoratedDetachedCriteria {

    private Class<? extends Entity<?>> entityClass;

    DetachedCriteria criteria;

    int firstResult = 0;
    int maxResults = -1;

    public SearchModel(Class<? extends Entity<?>> entityClass) {
        if (entityClass == null)
            throw new NullPointerException("entityClass");
        this.entityClass = entityClass;
    }

    public Class<? extends Entity<?>> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<? extends Entity<?>> entityClass) {
        if (entityClass == null)
            throw new NullPointerException("entityClass");
        this.entityClass = entityClass;
    }

    @Override
    protected DetachedCriteria getImpl() {
        if (criteria == null)
            criteria = DetachedCriteria.forClass(entityClass);
        return criteria;
    }

    public boolean isEmpty() {
        return (criteria == null && firstResult == 0 && maxResults < 0);
    }

    public DetachedCriteria getDetachedCriteria() {
        return criteria;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

}
