package com.bee32.plover.orm.ext.util;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.transform.ResultTransformer;

public abstract class DecoratedDetachedCriteria {

    protected abstract DetachedCriteria getImpl();

    public Criteria getExecutableCriteria(Session session) {
        return getImpl().getExecutableCriteria(session);
    }

    public DetachedCriteria add(Criterion criterion) {
        return getImpl().add(criterion);
    }

    public DetachedCriteria addOrder(Order order) {
        return getImpl().addOrder(order);
    }

    public DetachedCriteria createAlias(String associationPath, String alias)
            throws HibernateException {
        return getImpl().createAlias(associationPath, alias);
    }

    public DetachedCriteria createCriteria(String associationPath, String alias)
            throws HibernateException {
        return getImpl().createCriteria(associationPath, alias);
    }

    public DetachedCriteria createCriteria(String associationPath)
            throws HibernateException {
        return getImpl().createCriteria(associationPath);
    }

    public String getAlias() {
        return getImpl().getAlias();
    }

    public DetachedCriteria setFetchMode(String associationPath, FetchMode mode)
            throws HibernateException {
        return getImpl().setFetchMode(associationPath, mode);
    }

    public DetachedCriteria setProjection(Projection projection) {
        return getImpl().setProjection(projection);
    }

    public DetachedCriteria setResultTransformer(ResultTransformer resultTransformer) {
        return getImpl().setResultTransformer(resultTransformer);
    }

    public DetachedCriteria createAlias(String associationPath, String alias, int joinType)
            throws HibernateException {
        return getImpl().createAlias(associationPath, alias, joinType);
    }

    public DetachedCriteria createCriteria(String associationPath, int joinType)
            throws HibernateException {
        return getImpl().createCriteria(associationPath, joinType);
    }

    public DetachedCriteria createCriteria(String associationPath, String alias, int joinType)
            throws HibernateException {
        return getImpl().createCriteria(associationPath, alias, joinType);
    }

    public DetachedCriteria setComment(String comment) {
        return getImpl().setComment(comment);
    }

    public DetachedCriteria setLockMode(LockMode lockMode) {
        return getImpl().setLockMode(lockMode);
    }

    public DetachedCriteria setLockMode(String alias, LockMode lockMode) {
        return getImpl().setLockMode(alias, lockMode);
    }

}
