package com.bee32.plover.orm.entity;

import java.io.Serializable;

import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.criterion.Criterion;
import org.springframework.dao.DataAccessException;

public interface IEntityRepo_H<E extends IEntity<K>, K extends Serializable>
        extends IEntityRepo<E, K> {

    E retrieve(K key, LockMode lockMode)
            throws DataAccessException;

    void update(E entity, LockMode lockMode)
            throws DataAccessException;

    void delete(Object entity, LockMode lockMode)
            throws DataAccessException;

    void merge(E entity)
            throws DataAccessException;

    void evict(E entity)
            throws DataAccessException;

    void replicate(E entity, ReplicationMode replicationMode)
            throws DataAccessException;

    void flush();

    int count(Criterion... restrictions);

}
