package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletRequest;

import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.Component;
import com.bee32.plover.arch.util.IStruct;

// @Service
// /* @Lazy */@Scope("prototype")
public class EasTxWrapper<E extends Entity<? extends K>, K extends Serializable>
        extends Component
        implements IEntityAccessService<E, K> {

    EntityDao<E, K> dao;

    public EntityDao<E, K> getDao() {
        if (dao == null)
            throw new IllegalStateException("No DAO bound.");
        return dao;
    }

    public void setDao(EntityDao<E, K> dao) {
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
        return (Class<K>) dao.getKeyType();
    }

    @Override
    public Class<E> getObjectType() {
        return (Class<E>) dao.getObjectType();
    }

    @Override
    public Class<E> getEntityType() {
        return (Class<E>) dao.getEntityType();
    }

    @Transactional(readOnly = true)
    @Override
    public E load(K id) {
        checkLoad();
        return (E) getDao().load(id);
    }

    @Transactional(readOnly = true)
    @Override
    public E getUnique(Criterion... restrictions) {
        checkLoad();
        return (E) getDao().getUnique(restrictions);
    }

    @Override
    public E getUnique(DetachedCriteria criteria) {
        checkLoad();
        return (E) getDao().getUnique(criteria);
    }

    @Override
    public E getFirst(Criterion... restrictions) {
        checkLoad();
        return getDao().getFirst(restrictions);
    }

    @Override
    public E getFirst(DetachedCriteria criteria) {
        checkLoad();
        return getDao().getFirst(criteria);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean contains(Object entity) {
        return getDao().contains(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public K save(E entity) {
        checkSave();
        return (K) getDao().save(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public void update(E entity) {
        checkUpdate();
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
        K id = entity.getId();
        if (id == null)
            checkSave();
        else
            // EntitySpec?
            checkUpdate();
        getDao().saveOrUpdate(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public void saveAll(E... entities) {
        checkSave();
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
        checkSave();
        getDao().saveAll(entities);
    }

    @Transactional(readOnly = false)
    @Override
    public void saveOrUpdateAll(E... entities) {
        saveOrUpdateAll(Arrays.asList(entities));
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list(Criterion... restrictions) {
        checkList();
        return (List<E>) getDao().list(restrictions);
    }

    @Transactional(readOnly = false)
    @Override
    public void saveOrUpdateAll(Collection<? extends E> entities) {
        boolean haveNew = false;
        boolean haveOld = false;
        for (E entity : entities) {
            K id = entity.getId();
            if (id == null)
                haveNew = true;
            else
                haveOld = true;
            if (haveNew && haveOld)
                break;
        }

        if (haveNew)
            checkSave();
        if (haveOld)
            checkUpdate();

        getDao().saveOrUpdateAll(entities);
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(Object entity) {
        checkDelete();
        getDao().delete(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public E get(K key) {
        checkLoad();
        return (E) getDao().get(key);
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list(Order order, Criterion... restrictions) {
        checkList();
        return (List<E>) getDao().list(order, restrictions);
    }

    @Transactional(readOnly = true)
    @Override
    public E lazyLoad(K id) {
        checkLoad();
        // XXX the lazy-init entity returned seems not usable outside of Tx.
        return (E) getDao().lazyLoad(id);
    }

    @Transactional(readOnly = true)
    @Override
    public E retrieve(K key, LockMode lockMode)
            throws DataAccessException {
        checkLoad();
        return (E) getDao().retrieve(key, lockMode);
    }

    @Transactional(readOnly = false)
    @Override
    public void update(E entity, LockMode lockMode)
            throws DataAccessException {
        checkUpdate();
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
        checkList();
        return (List<E>) getDao().list(offset, limit, restrictions);
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list() {
        checkList();
        return (List<E>) getDao().list();
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(Object entity, LockMode lockMode)
            throws DataAccessException {
        checkDelete();
        getDao().delete(entity, lockMode);
    }

    @Transactional(readOnly = false)
    @Override
    public E merge(E entity)
            throws DataAccessException {
        checkMerge();
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
        checkList();
        return (List<E>) getDao().list(order, offset, limit, restrictions);
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list(int offset, int limit, DetachedCriteria criteria) {
        checkList();
        return (List<E>) getDao().list(offset, limit, criteria);
    }

    @Override
    public List<E> list(DetachedCriteria criteria) {
        checkList();
        return (List<E>) getDao().list(criteria);
    }

    @Transactional(readOnly = true)
    @Override
    public int count(Criterion... restrictions) {
        checkCount();
        return getDao().count(restrictions);
    }

    @Override
    public int count(DetachedCriteria criteria) {
        checkCount();
        return getDao().count(criteria);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteById(K id) {
        checkDelete();
        getDao().deleteById(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteAll(Criterion... restrictions) {
        checkDelete();
        getDao().deleteAll(restrictions);
    }

    @Override
    public void deleteAll(DetachedCriteria criteria) {
        checkDelete();
        getDao().deleteAll(criteria);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteByKey(K key) {
        checkDelete();
        getDao().deleteByKey(key);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteAll() {
        checkDelete();
        getDao().deleteAll();
    }

    @Transactional(readOnly = true)
    @Override
    public int count() {
        checkCount();
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

    protected void checkLoad() {
    }

    protected void checkCount() {
    }

    protected void checkList() {
    }

    protected void checkMerge() {
    }

    protected void checkSave() {
    }

    protected void checkUpdate() {
    }

    protected void checkDelete() {
    }

}
