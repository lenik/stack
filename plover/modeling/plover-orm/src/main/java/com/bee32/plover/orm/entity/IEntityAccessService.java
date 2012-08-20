package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.dao.DataAccessException;

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
     * @throws DataAccessException
     * @see Criteria#uniqueResult()
     */
    E getUnique(ICriteriaElement... criteriaElements)
            throws NonUniqueException, DataAccessException;

    /**
     * Get the first result with restrictions.
     *
     * @param restrictions
     *            Restrictions to the selection. (AND).
     * @return The first matched entity, or <code>null</code> if none matched.
     * @see #getUnique(Criterion...)
     */
    E getFirst(ICriteriaElement... criteriaElements)
            throws DataAccessException;

    /**
     * Retrieve an object by name.
     */
    E getByName(String name)
            throws DataAccessException;

    /**
     * List entities with restrictions.
     *
     * @param restrictions
     *            Restrictions to the selection. (AND).
     * @return Non-<code>null</code> result list.
     */
    List<E> list(ICriteriaElement... criteriaElements)
            throws DataAccessException;

    /**
     * List entities with restrictions.
     *
     * @param restrictions
     *            Restrictions to the selection. (AND).
     * @return Non-<code>null</code> result list.
     */
    <T> List<T> listMisc(ICriteriaElement... criteriaElements)
            throws DataAccessException;

    <T> T getMisc(ICriteriaElement... criteriaElements)
            throws DataAccessException;

    <T extends Number> T sum(String propertyName, ICriteriaElement... criteriaElements)
            throws DataAccessException;

    <T extends Number> T average(String propertyName, ICriteriaElement... criteriaElements)
            throws DataAccessException;

    <T extends Number> T min(String propertyName, ICriteriaElement... criteriaElements)
            throws DataAccessException;

    <T extends Number> T max(String propertyName, ICriteriaElement... criteriaElements)
            throws DataAccessException;

    /**
     * Count of entities with restrictions.
     *
     * @param restrictions
     *            Restrictions to the selection. (AND).
     * @return Number of entities.
     */
    int count(ICriteriaElement... criteriaElements)
            throws DataAccessException;

    boolean deleteById(K id)
            throws DataAccessException;

    /**
     * Delete entities with restrictions.
     *
     * @param restrictions
     *            Restrictions to the selection. (AND).
     * @return Number of entities actually deleted. This is an approx number however.
     */
    int findAndDelete(ICriteriaElement... criteriaElements)
            throws DataAccessException;

}
