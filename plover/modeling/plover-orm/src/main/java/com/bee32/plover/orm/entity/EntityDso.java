package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletRequest;

import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.criterion.Criterion;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.EnterpriseService;
import com.bee32.plover.arch.util.IStruct;
import com.bee32.plover.inject.ComponentTemplate;

@Transactional(readOnly = true)
@ComponentTemplate
@Lazy
public abstract class EntityDso<E extends Entity<K>, K extends Serializable>
        extends EnterpriseService
        implements IEntityRepo_H<E, K> {

    protected abstract EntityDao<E, K> getDao();

    /**
     * <pre>
     * 1. IRepository
     * </pre>
     */

    @Override
    public boolean containsKey(Object key) {
        return getDao().containsKey(key);
    }

    @Override
    public E get(K key) {
        return getDao().get(key);
    }

    @Override
    public E load(K key) {
        return getDao().load(key);
    }

    @Override
    public Collection<K> keys() {
        return getDao().keys();
    }

    @Override
    public List<E> list() {
        return getDao().list();
    }

    @Override
    public E populate(IStruct struct)
            throws BuildException {
        return getDao().populate(struct);
    }

    @Override
    public E populate(ServletRequest request)
            throws BuildException {
        return getDao().populate(request);
    }

    @Override
    public boolean populate(E obj, IStruct struct)
            throws BuildException {
        return getDao().populate(obj, struct);
    }

    @Override
    public void deleteByKey(K key) {
        getDao().deleteByKey(key);
    }

    @Override
    public void deleteAll() {
        getDao().deleteAll();
    }

    @Override
    public int count() {
        return getDao().count();
    }

    /**
     * <pre>
     * 2. IEntityRepo
     * </pre>
     */

    @Override
    public K getKey(E entity) {
        return getDao().getKey(entity);
    }

    @Override
    public boolean contains(Object entity) {
        return getDao().contains(entity);
    }

    @Override
    public K save(E entity) {
        return getDao().save(entity);
    }

    @Override
    public void update(E entity) {
        getDao().update(entity);
    }

    @Override
    public void refresh(E entity) {
        getDao().refresh(entity);
    }

    @Override
    public void saveOrUpdate(E entity) {
        getDao().saveOrUpdate(entity);
    }

    @Override
    public void delete(Object entity) {
        getDao().delete(entity);
    }

    /**
     * <pre>
     * 3. IEntityRepo_H
     * </pre>
     */

    @Override
    public E retrieve(K key, LockMode lockMode)
            throws DataAccessException {
        return getDao().retrieve(key, lockMode);
    }

    @Override
    public void update(E entity, LockMode lockMode)
            throws DataAccessException {
        getDao().update(entity, lockMode);
    }

    @Override
    public void delete(Object entity, LockMode lockMode)
            throws DataAccessException {
        getDao().delete(entity, lockMode);
    }

    @Override
    public void merge(E entity)
            throws DataAccessException {
        getDao().merge(entity);
    }

    @Override
    public void evict(E entity)
            throws DataAccessException {
        getDao().evict(entity);
    }

    @Override
    public void replicate(E entity, ReplicationMode replicationMode)
            throws DataAccessException {
        getDao().replicate(entity, replicationMode);
    }

    @Override
    public void flush() {
        getDao().flush();
    }

    @Override
    public int count(Criterion... restrictions) {
        return getDao().count(restrictions);
    }

}
