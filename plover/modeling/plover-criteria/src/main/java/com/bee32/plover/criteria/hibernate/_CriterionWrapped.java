package com.bee32.plover.criteria.hibernate;

import javax.free.IllegalUsageException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.TypedValue;

public abstract class _CriterionWrapped
        implements Criterion {

    private static final long serialVersionUID = 1L;

    @Override
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
            throws HibernateException {
        throw new IllegalUsageException();
    }

    @Override
    public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
            throws HibernateException {
        throw new IllegalUsageException();
    }

}
