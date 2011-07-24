package com.bee32.plover.orm.entity;

import java.io.Serializable;

import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.springframework.dao.DataAccessException;

import com.bee32.plover.orm.dao.HibernateTemplate;

public interface IEntityRepo_H<E extends IEntity<? extends K>, K extends Serializable>
        extends IEntityRepo<E, K> {

    /**
     * Return the persistent instance of the given entity class with the given identifier, throwing
     * an exception if not found.
     *
     * <p>
     * (Override {@link #getOrFail(Serializable)} to distinguish with {@link #lazyLoad(Serializable)}.
     *
     * <p>
     * <b>WARNING</b>: This is different to {@link HibernateTemplate#load(Class, Serializable)}
     * which is lazy-initialized.
     *
     * @param entityClass
     *            a persistent class
     * @param id
     *            the identifier of the persistent instance
     * @return the persistent instance
     * @throws org.springframework.orm.ObjectRetrievalFailureException
     *             if not found
     * @throws org.springframework.dao.DataAccessException
     *             in case of Hibernate errors
     */
    @Override
    E getOrFail(K id);

    /**
     * @see HibernateTemplate#load(Class, Serializable).
     */
    E lazyLoad(K id);

    E retrieve(K key, LockMode lockMode)
            throws DataAccessException;

    void update(E entity, LockMode lockMode)
            throws DataAccessException;

    void delete(Object entity, LockMode lockMode)
            throws DataAccessException;

    E merge(E entity)
            throws DataAccessException;

    void evict(E entity)
            throws DataAccessException;

    void replicate(E entity, ReplicationMode replicationMode)
            throws DataAccessException;

    void flush();

}
