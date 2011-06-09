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
        List<E> list = getDao().list();
        return list;
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
        if (entity == null)
            throw new NullPointerException("entity");
        _beforeUpdate(entity);

        K key = getDao().save(entity);

        _afterUpdate(entity);
        return key;
    }

    @Override
    public void update(E entity) {
        if (entity == null)
            throw new NullPointerException("entity");
        _beforeUpdate(entity);

        getDao().update(entity);

        _afterUpdate(entity);
    }

    @Override
    public void refresh(E entity) {
        getDao().refresh(entity);
    }

    @Override
    public void saveOrUpdate(E entity) {
        if (entity == null)
            throw new NullPointerException("entity");
        _beforeUpdate(entity);

        getDao().saveOrUpdate(entity);

        _afterUpdate(entity);
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
        E entity = getDao().retrieve(key, lockMode);
        if (entity != null)
            _afterLoad(entity);
        return entity;
    }

    @Override
    public void update(E entity, LockMode lockMode)
            throws DataAccessException {
        if (entity != null)
            _beforeUpdate(entity);

        getDao().update(entity, lockMode);

        _afterUpdate(entity);
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

    /**
     * Post initialization for entities.
     *
     * @param entity
     *            Non-<code>null</code> entity.
     * @return Generally return the same entity.
     */
    protected E _afterLoad(E entity) { // checkOut
        return entity;
    }

    protected void _beforeUpdate(E entity) { // checkIn
    }

    protected void _afterUpdate(E entity) {
    }

}
