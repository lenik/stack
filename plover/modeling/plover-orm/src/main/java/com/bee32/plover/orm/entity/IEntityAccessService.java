package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

public interface IEntityAccessService<E extends Entity<? extends K>, K extends Serializable>
        extends IEntityRepo_H<E, K> {

    /**
     * Get the unique result with restrictions.
     *
     * @param restrictions
     *            Restrictions to the selection. (AND).
     * @return The matched entity, or <code>null</code> if none matched.
     * @throws HibernateException
     *             If there is more than one matching result
     * @see Criteria#uniqueResult()
     */
    E getUnique(Criterion... restrictions);

    /**
     * List entities with restrictions.
     *
     * @param restrictions
     *            Restrictions to the selection. (AND).
     * @return Non-<code>null</code> result list.
     */
    List<E> list(Criterion... restrictions);

    /**
     * List entities with restrictions.
     *
     * @param order
     *            Sort the result list using the specific {@link Order order}.
     * @param restrictions
     *            Restrictions to the selection. (AND).
     * @return Non-<code>null</code> result list.
     */
    List<E> list(Order order, Criterion... restrictions);

    /**
     * List entities with restrictions.
     *
     * @param offset
     *            The first result to retrieve, numbered from 0
     * @param limit
     *            Max number of results to get.
     * @param restrictions
     *            Restrictions to the selection. (AND).
     * @return Non-<code>null</code> result list.
     */
    List<E> list(int offset, int limit, Criterion... restrictions);

    /**
     * List entities with restrictions.
     *
     * @param order
     *            Sort the result list using the specific {@link Order order}.
     * @param offset
     *            The first result to retrieve, numbered from 0
     * @param limit
     *            Max number of results to get.
     * @param restrictions
     *            Restrictions to the selection. (AND).
     * @return Non-<code>null</code> result list.
     */
    List<E> list(Order order, int offset, int limit, Criterion... restrictions);

    List<E> list(int offset, int limit, DetachedCriteria criteria);

    /**
     * Count of entities with restrictions.
     *
     * @param restrictions
     *            Restrictions to the selection. (AND).
     * @return Number of entities.
     */
    int count(Criterion... restrictions);

    void delete(K id);

    /**
     * Delete entities with restrictions.
     *
     * @param restrictions
     *            Restrictions to the selection. (AND).
     */
    void delete(Criterion... restrictions);

}
