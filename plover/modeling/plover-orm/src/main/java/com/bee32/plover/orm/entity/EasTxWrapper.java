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

    EntityDao<? super E, ? super K> dao;

    public EntityDao<? super E, ? super K> getDao() {
        if (dao == null)
            throw new IllegalStateException("No DAO bound.");
        return dao;
    }

    public void setDao(EntityDao<? super E, ? super K> dao) {
        if (dao == null)
            throw new NullPointerException("dao");
        this.dao = dao;
    }

    @Override
    public K getKey(E entity) {
        return (K) getDao().getKey(entity);
    }

    @Override
    public Class<K> getKeyType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<? extends E> getObjectType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<? extends E> getEntityType() {
        throw new UnsupportedOperationException();
    }

    @Transactional(readOnly = true)
    @Override
    public E load(K id) {
        return (E) getDao().load(id);
    }

    @Transactional(readOnly = true)
    @Override
    public E getUnique(Criterion... restrictions) {
        return (E) getDao().getUnique(restrictions);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean contains(Object entity) {
        return getDao().contains(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public K save(E entity) {
        return (K) getDao().save(entity);
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
        return (List<E>) getDao().list(restrictions);
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
        return (E) getDao().get(key);
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list(Order order, Criterion... restrictions) {
        return (List<E>) getDao().list(order, restrictions);
    }

    @Transactional(readOnly = true)
    @Override
    public E _load(K id) {
        // XXX the lazy-init entity returned seems not usable outside of Tx.
        return (E) getDao()._load(id);
    }

    @Transactional(readOnly = true)
    @Override
    public E retrieve(K key, LockMode lockMode)
            throws DataAccessException {
        return (E) getDao().retrieve(key, lockMode);
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
        return (Collection<K>) getDao().keys();
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list(int offset, int limit, Criterion... restrictions) {
        return (List<E>) getDao().list(offset, limit, restrictions);
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list() {
        return (List<E>) getDao().list();
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(Object entity, LockMode lockMode)
            throws DataAccessException {
        getDao().delete(entity, lockMode);
    }

    @Transactional(readOnly = false)
    @Override
    public E merge(E entity)
            throws DataAccessException {
        return (E) getDao().merge(entity);
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

    // XXX Does flush useful here?
    @Override
    public void flush() {
        getDao().flush();
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list(Order order, int offset, int limit, Criterion... restrictions) {
        return (List<E>) getDao().list(order, offset, limit, restrictions);
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list(int offset, int limit, DetachedCriteria criteria) {
        return (List<E>) getDao().list(offset, limit, criteria);
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
    public void deleteAll(Criterion... restrictions) {
        getDao().deleteAll(restrictions);
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

    @Override
    public boolean populate(E obj, IStruct struct)
            throws BuildException {
        throw new UnsupportedOperationException("You should populate using the explicit DAO class.");
    }

    @Override
    public E populate(IStruct struct)
            throws BuildException {
        throw new UnsupportedOperationException("You should populate using the explicit DAO class.");
    }

    @Override
    public E populate(ServletRequest request)
            throws BuildException {
        throw new UnsupportedOperationException("You should populate using the explicit DAO class.");
    }

}
