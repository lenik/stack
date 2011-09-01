package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.free.IllegalUsageError;
import javax.servlet.ServletRequest;

import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.Component;
import com.bee32.plover.arch.util.IStruct;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public abstract class EasTxWrapper<E extends Entity<? extends K>, K extends Serializable>
        extends Component
        implements IEntityAccessService<E, K> {

    EntityDao<E, K> dao;
    boolean autoFlush = true;
    boolean autoBulkFlush = true;

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
    public E getOrFail(K id) {
        checkLoad();
        return (E) getDao().getOrFail(id);
    }

    @Transactional(readOnly = true)
    @Override
    public E getUnique(ICriteriaElement... criteria) {
        checkLoad();
        return (E) getDao().getUnique(criteria);
    }

    @Override
    public E getFirst(ICriteriaElement... criteria) {
        checkLoad();
        return getDao().getFirst(criteria);
    }

    @Override
    public E getByName(String name) {
        checkLoad();
        return getDao().getByName(name);
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

        EntityDao<E, K> dao = getDao();
        K id = (K) dao.save(entity);

        autoFlush();
        return id;
    }

    @Transactional(readOnly = false)
    @Override
    public void update(E entity) {
        checkUpdate();

        EntityDao<E, K> dao = getDao();
        dao.update(entity);

        autoFlush();
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

        autoFlush();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = false)
    @Override
    public void saveAll(E... entities) {
        checkSave();

        EntityDao<E, K> dao = getDao();
        dao.saveAll(entities);

        autoBulkFlush();
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
        autoBulkFlush();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = false)
    @Override
    public void saveOrUpdateAll(E... entities) {
        saveOrUpdateAll(Arrays.asList(entities));
        autoBulkFlush();
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list(ICriteriaElement... criteria) {
        checkList();
        return (List<E>) getDao().list(criteria);
    }

    @Transactional(readOnly = true)
    @Override
    public <T> List<T> listMisc(ICriteriaElement... criteriaElements) {
        checkList();
        return getDao().listMisc(criteriaElements);
    }

    @Transactional(readOnly = true)
    @Override
    public <T> T getMisc(ICriteriaElement... criteriaElements) {
        return getDao().getMisc(criteriaElements);
    }

    @Transactional(readOnly = true)
    @Override
    public <T extends Number> T sum(String propertyName, ICriteriaElement... criteriaElements) {
        return getDao().sum(propertyName, criteriaElements);
    }

    @Transactional(readOnly = true)
    @Override
    public <T extends Number> T average(String propertyName, ICriteriaElement... criteriaElements) {
        return getDao().average(propertyName, criteriaElements);
    }

    @Transactional(readOnly = true)
    @Override
    public <T extends Number> T min(String propertyName, ICriteriaElement... criteriaElements) {
        return getDao().min(propertyName, criteriaElements);
    }

    @Transactional(readOnly = true)
    @Override
    public <T extends Number> T max(String propertyName, ICriteriaElement... criteriaElements) {
        return getDao().max(propertyName, criteriaElements);
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

        EntityDao<E, K> dao = getDao();
        dao.saveOrUpdateAll(entities);

        autoBulkFlush();
    }

    @Transactional(readOnly = false)
    @Override
    public boolean delete(Object entity) {
        checkDelete();

        EntityDao<E, K> dao = getDao();
        boolean status = dao.delete(entity);

        autoFlush();

        return status;
    }

    @Transactional(readOnly = true)
    @Override
    public E get(K key) {
        checkLoad();
        return (E) getDao().get(key);
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
        autoFlush();
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<K> keys() {
        return (Collection<K>) getDao().keys();
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> list() {
        checkList();
        return (List<E>) getDao().list();
    }

    @Transactional(readOnly = false)
    @Override
    public boolean delete(Object entity, LockMode lockMode)
            throws DataAccessException {
        checkDelete();
        boolean status = getDao().delete(entity, lockMode);
        autoFlush();
        return status;
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
        EntityDao<E, K> dao = getDao();
        // Comment out for strict mode.
        // dao.flush();
        dao.evict(entity);
        // autoFlush();
    }

    // TODO How to use replicate?
    @Transactional(readOnly = false)
    @Override
    public void replicate(E entity, ReplicationMode replicationMode)
            throws DataAccessException {
        getDao().replicate(entity, replicationMode);
    }

    @Override
    @Deprecated
    public void flush() {
        throw new IllegalUsageError("ETW.flush should never be called.");
    }

    @Transactional(readOnly = true)
    @Override
    public int count(ICriteriaElement... criteria) {
        checkCount();
        return getDao().count(criteria);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean deleteById(K id) {
        checkDelete();
        boolean status = getDao().deleteById(id);
        autoFlush();
        return status;
    }

    @Transactional(readOnly = false)
    @Override
    public int deleteAll(ICriteriaElement... criteria) {
        checkDelete();
        int count = getDao().deleteAll(criteria);
        autoBulkFlush();
        return count;
    }

    @Transactional(readOnly = false)
    @Override
    public boolean deleteByKey(K key) {
        checkDelete();

        EntityDao<E, K> dao = getDao();
        boolean status = dao.deleteByKey(key);
        autoFlush();
        return status;
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteAll() {
        checkDelete();
        getDao().deleteAll();
        autoBulkFlush();
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

    protected void autoFlush() {
        getDao().flush();
    }

    protected void autoBulkFlush() {
        autoFlush();
    }
}
