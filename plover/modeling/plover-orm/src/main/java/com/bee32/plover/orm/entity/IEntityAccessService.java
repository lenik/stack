package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
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
     * @throws NonUniqueException
     *             If there is more than one matching result
     * @see Criteria#uniqueResult()
     */
    E getUnique(ICriteriaElement... criteriaElements)
            throws NonUniqueException;

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
     * List entities with restrictions.
     *
     * @param restrictions
     *            Restrictions to the selection. (AND).
     * @return Non-<code>null</code> result list.
     */
    <T> List<T> listMisc(ICriteriaElement... criteriaElements);

    <T> T getMisc(ICriteriaElement... criteriaElements);

    <T extends Number> T sum(String propertyName, ICriteriaElement... criteriaElements);

    <T extends Number> T average(String propertyName, ICriteriaElement... criteriaElements);

    <T extends Number> T min(String propertyName, ICriteriaElement... criteriaElements);

    <T extends Number> T max(String propertyName, ICriteriaElement... criteriaElements);

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
     * @return Number of entities actually deleted. This is an approx number however.
     */
    int findAndDelete(ICriteriaElement... criteriaElements);

}
