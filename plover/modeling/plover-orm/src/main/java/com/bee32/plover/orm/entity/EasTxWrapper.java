package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletRequest;

import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.Component;
import com.bee32.plover.arch.util.IStruct;

@Service
@Scope("prototype")
public class EasTxWrapper<E extends Entity<? extends K>, K extends Serializable>
        extends Component
        implements IEntityAccessService<E, K> {

    IEntityAccessService<E, K> dao;

    public IEntityAccessService<E, K> getDao() {
        if (dao == null)
            throw new IllegalStateException("No DAO bound.");
        return dao;
    }

    public void setDao(IEntityAccessService<E, K> dao) {
        if (dao == null)
            throw new NullPointerException("dao");
        this.dao = dao;
    }

    @Override
    public Class<? extends E> getEntityType() {
        return getDao().getEntityType();
    }

    @Override
    public K getKey(E entity) {
        return getDao().getKey(entity);
    }

    @Override
    public Class<K> getKeyType() {
        return getDao().getKeyType();
    }

    @Override
    public Class<? extends E> getObjectType() {
        return getDao().getObjectType();
    }

    @Transactional(readOnly = true)
    @Override
    public E load(K id) {
        return getDao().load(id);
    }

    @Transactional(readOnly = true)
    @Override
    public E getUnique(Criterion... restrictions) {
        return getDao().getUnique(restrictions);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean contains(Object entity) {
        return getDao().contains(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public K save(E entity) {
        return getDao().save(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public void update(E entity) {
        getDao().update(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public void refresh(E entity) {
        getDao().refresh(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public void saveOrUpdate(E entity) {
        getDao().saveOrUpdate(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public void saveAll(E... entities) {
        getDao().saveAll(entities);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean containsKey(Object key) {
        return getDao().containsKey(key);
    }

    @Transactional(readOnly = false)
    @Override
    public void saveAll(Collection<? extends E> entities) {
        getDao().saveAll(entities);
    }

    @Transactional(readOnly = false)
    @Override
    public void saveOrUpdateAll(E... entities) {
        getDao().saveOrUpdateAll(entities);
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list(Criterion... restrictions) {
        return getDao().list(restrictions);
    }

    @Transactional(readOnly = false)
    @Override
    public void saveOrUpdateAll(Collection<? extends E> entities) {
        getDao().saveOrUpdateAll(entities);
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(Object entity) {
        getDao().delete(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public E get(K key) {
        return getDao().get(key);
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list(Order order, Criterion... restrictions) {
        return getDao().list(order, restrictions);
    }

    @Transactional(readOnly = true)
    @Override
    public E _load(K id) {
        // XXX the lazy-init entity returned seems not usable outside of Tx.
        return getDao()._load(id);
    }

    @Transactional(readOnly = true)
    @Override
    public E retrieve(K key, LockMode lockMode)
            throws DataAccessException {
        return getDao().retrieve(key, lockMode);
    }

    @Transactional(readOnly = false)
    @Override
    public void update(E entity, LockMode lockMode)
            throws DataAccessException {
        getDao().update(entity, lockMode);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<K> keys() {
        return getDao().keys();
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list(int offset, int limit, Criterion... restrictions) {
        return getDao().list(offset, limit, restrictions);
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list() {
        return getDao().list();
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(Object entity, LockMode lockMode)
            throws DataAccessException {
        getDao().delete(entity, lockMode);
    }

    @Override
    public E populate(IStruct struct)
            throws BuildException {
        return getDao().populate(struct);
    }

    @Transactional(readOnly = false)
    @Override
    public E merge(E entity)
            throws DataAccessException {
        return getDao().merge(entity);
    }

    @Override
    public void evict(E entity)
            throws DataAccessException {
        getDao().evict(entity);
    }

    // TODO How to use replicate?
    @Transactional(readOnly = false)
    @Override
    public void replicate(E entity, ReplicationMode replicationMode)
            throws DataAccessException {
        getDao().replicate(entity, replicationMode);
    }

    @Override
    public E populate(ServletRequest request)
            throws BuildException {
        return getDao().populate(request);
    }

    // XXX Does flush useful here?
    @Override
    public void flush() {
        getDao().flush();
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list(Order order, int offset, int limit, Criterion... restrictions) {
        return getDao().list(order, offset, limit, restrictions);
    }

    @Override
    public boolean populate(E obj, IStruct struct)
            throws BuildException {
        return getDao().populate(obj, struct);
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list(int offset, int limit, DetachedCriteria criteria) {
        return getDao().list(offset, limit, criteria);
    }

    @Transactional(readOnly = true)
    @Override
    public int count(Criterion... restrictions) {
        return getDao().count(restrictions);
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(K id) {
        getDao().delete(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(Criterion... restrictions) {
        getDao().delete(restrictions);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteByKey(K key) {
        getDao().deleteByKey(key);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteAll() {
        getDao().deleteAll();
    }

    @Transactional(readOnly = true)
    @Override
    public int count() {
        return getDao().count();
    }

}
