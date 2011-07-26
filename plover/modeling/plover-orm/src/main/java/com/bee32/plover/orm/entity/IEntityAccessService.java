package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;

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
    E getUnique(ICriteriaElement... criteriaElements);

    /**
     * Get the first result with restrictions.
     *
     * @param restrictions
     *            Restrictions to the selection. (AND).
     * @return The first matched entity, or <code>null</code> if none matched.
     * @see #getUnique(Criterion...)
     */
    E getFirst(ICriteriaElement... criteriaElements);

    /**
     * Retrieve an object by name.
     */
    E getByName(String name);

    /**
     * List entities with restrictions.
     *
     * @param restrictions
     *            Restrictions to the selection. (AND).
     * @return Non-<code>null</code> result list.
     */
    List<E> list(ICriteriaElement... criteriaElements);

    /**
     * Count of entities with restrictions.
     *
     * @param restrictions
     *            Restrictions to the selection. (AND).
     * @return Number of entities.
     */
    int count(ICriteriaElement... criteriaElements);

    boolean deleteById(K id);

    /**
     * Delete entities with restrictions.
     *
     * @param restrictions
     *            Restrictions to the selection. (AND).
     */
    void deleteAll(ICriteriaElement... criteriaElements);

}
