package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Collection;

import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.bee32.plover.orm.dao.HibernateTemplate;

public interface IEntityRepo_H<E extends IEntity<? extends K>, K extends Serializable>
        extends IEntityRepo<E, K> {

    /**
     * Persist the given transient instance, first assigning a generated identifier. (Or using the
     * current value of the identifier property if the <tt>assigned</tt> generator is used.) This
     * operation cascades to associated instances if the association is mapped with
     * <tt>cascade="save-update"</tt>.
     *
     * @param object
     *            a transient instance of a persistent class
     * @return the generated identifier
     * @see Session#save(Object)
     */
    @Override
    K save(E entity)
            throws DataAccessException;

    /**
     * Return the persistent instance of the given entity class with the given identifier, throwing
     * an exception if not found.
     *
     * <p>
     * (Override {@link #getOrFail(Serializable)} to distinguish with
     * {@link #lazyLoad(Serializable)}.
     *
     * <p>
     * <b>WARNING</b>: This is different to {@link HibernateTemplate#load(Class, Serializable)}
     * which is lazy-initialized.
     *
     * @param entityClass
     *            Apersistent class.
     * @param id
     *            The identifier of the persistent instance.
     * @return the persistent instance
     * @throws ObjectRetrievalFailureException
     *             If record with specified id is not found.
     * @throws DataAccessException
     *             Other data access exceptions.
     */
    @Override
    E getOrFail(K id)
            throws DataAccessException;

    /**
     * @see HibernateTemplate#load(Class, Serializable).
     */
    E lazyLoad(K id);

    E retrieve(K key, LockMode lockMode)
            throws DataAccessException;

    void update(E entity, LockMode lockMode)
            throws DataAccessException;

    boolean delete(Object entity, LockMode lockMode)
            throws DataAccessException;

    int deleteAll(Collection<?> entities, LockMode lockMode)
            throws DataAccessException;

    /**
     * Copy the state of the given object onto the persistent object with the same identifier. If
     * there is no persistent instance currently associated with the session, it will be loaded.
     * Return the persistent instance. If the given instance is unsaved, save a copy of and return
     * it as a newly persistent instance. The given instance does not become associated with the
     * session. This operation cascades to associated instances if the association is mapped with
     * <tt>cascade="merge"</tt>.<br>
     * <br>
     * The semantics of this method are defined by JSR-220.
     *
     * @param object
     *            a detached instance with state to be copied
     * @return an updated persistent instance
     * @see Session#merge(Object)
     */
    @Deprecated
    E merge(E entity)
            throws DataAccessException;

    /**
     * Remove this instance from the session cache. Changes to the instance will not be synchronized
     * with the database. This operation cascades to associated instances if the association is
     * mapped with <tt>cascade="evict"</tt>.
     *
     * @param object
     *            a persistent instance
     * @see Session#evict(Object)
     */
    void evict(E entity)
            throws DataAccessException;

    /**
     * Persist the state of the given detached instance, reusing the current identifier value. This
     * operation cascades to associated instances if the association is mapped with
     * <tt>cascade="replicate"</tt>.
     *
     * @param object
     *            a detached instance of a persistent class
     * @see Session#replicate(Object,ReplicationMode)
     */
    @Deprecated
    void replicate(E entity, ReplicationMode replicationMode)
            throws DataAccessException;

    /**
     * Force this session to flush. Must be called at the end of a unit of work, before commiting
     * the transaction and closing the session (depending on {@link #setFlushMode flush-mode},
     * {@link Transaction#commit()} calls this method).
     * <p/>
     * <i>Flushing</i> is the process of synchronizing the underlying persistent store with
     * persistable state held in memory.
     *
     * @see Session#flush()
     */
    @Deprecated
    void flush()
            throws DataAccessException;

}
