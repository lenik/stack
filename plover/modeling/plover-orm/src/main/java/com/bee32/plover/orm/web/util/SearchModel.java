package com.bee32.plover.orm.web.util;

import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.Limit;
import com.bee32.plover.orm.entity.Entity;

/**
 * 搜索模型
 */
public class SearchModel
        extends CriteriaComposite {

    private static final long serialVersionUID = 1L;

    private Class<? extends Entity<?>> entityClass;

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

    public boolean isDummy() {
        if (firstResult != 0 || maxResults != 0)
            return false;
        if (!elements.isEmpty())
            return false;
        return true;
    }

    public CriteriaComposite compose() {
        CriteriaComposite composite = new CriteriaComposite();
        Limit limit = new Limit(firstResult, maxResults);
        composite.add(limit);
        composite.addAll(elements);
        return composite;
    }

}
