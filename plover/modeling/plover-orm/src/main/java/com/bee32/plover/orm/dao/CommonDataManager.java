package com.bee32.plover.orm.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.ReplicationMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.entity.IEntity;

@Service
@Lazy
public class CommonDataManager
        extends HibernateDaoSupport {

    static Logger logger = LoggerFactory.getLogger(CommonDataManager.class);

    // XXX - how to not use dao-support?
    // SessionFactory sessionFactory;

    /**
     * Set <code>true</code> for debug purpose.
     */
    public boolean evictImmediately = true;

    public boolean isEvictImmediately() {
        return evictImmediately;
    }

    public void setEvictImmediately(boolean evictImmediately) {
        this.evictImmediately = evictImmediately;
    }

    @Transactional
    public void saveAll(Collection<?> entities) {
        saveAll(null, entities);
    }

    @Transactional
    public void saveAll(IEntity<?>... entities) {
        saveAll(null, Arrays.asList(entities));
    }

    @Transactional
    public void saveAll(String entityName, Collection<?> entities) {
        HibernateTemplate template = getHibernateTemplate();
        // HibernateTemplate getHibernateTemplate() = new HibernateTemplate(sessionFactory);

        boolean _saveOrUpdate = true;

        if (_saveOrUpdate)
            template.saveOrUpdateAll(entities);

        else
            for (Object entity : entities)
                if (entityName == null)
                    template.save(entity);
                else
                    template.save(entityName, entity);

        try {
            template.flush();
        } catch (DataAccessException e) {
            DataAccessExceptionUtil.dumpAndThrow(e);
        }

        if (evictImmediately) {
            for (Object entity : entities)
                template.evict(entity);
        }
    }

    public int getFlushMode() {
        return getHibernateTemplate().getFlushMode();
    }

    public void setFlushMode(int flushMode) {
        getHibernateTemplate().setFlushMode(flushMode);
    }

    @Transactional
    public void flush() {
        getHibernateTemplate().flush();
    }

    @Transactional
    public <T> T execute(HibernateCallback<T> action)
            throws DataAccessException {
        return getHibernateTemplate().execute(action);
    }

    @Transactional
    public List<?> executeFind(HibernateCallback<?> action)
            throws DataAccessException {
        return getHibernateTemplate().executeFind(action);
    }

    @Transactional(readOnly = true)
    public <T> T get(Class<T> entityClass, Serializable id)
            throws DataAccessException {
        return getHibernateTemplate().get(entityClass, id);
    }

    @Transactional(readOnly = true)
    public <T> T get(Class<T> entityClass, Serializable id, LockMode lockMode)
            throws DataAccessException {
        return getHibernateTemplate().get(entityClass, id, lockMode);
    }

    @Transactional(readOnly = true)
    public Object get(String entityName, Serializable id)
            throws DataAccessException {
        return getHibernateTemplate().get(entityName, id);
    }

    @Transactional(readOnly = true)
    public Object get(String entityName, Serializable id, LockMode lockMode)
            throws DataAccessException {
        return getHibernateTemplate().get(entityName, id, lockMode);
    }

    @Transactional(readOnly = true)
    public <T> T fetch(Class<T> entityClass, Serializable id)
            throws DataAccessException {
        T entity = getHibernateTemplate().get(entityClass, id);
        if (entity == null)
            throw new ObjectNotFoundException(id, entityClass.getName());
        return entity;
    }

    @Transactional(readOnly = true)
    public <T> T _load(Class<T> entityClass, Serializable id)
            throws DataAccessException {
        return getHibernateTemplate().load(entityClass, id);
    }

    @Transactional(readOnly = true)
    public <T> T _load(Class<T> entityClass, Serializable id, LockMode lockMode)
            throws DataAccessException {
        return getHibernateTemplate().load(entityClass, id, lockMode);
    }

    @Transactional(readOnly = true)
    public Object _load(String entityName, Serializable id)
            throws DataAccessException {
        return getHibernateTemplate().load(entityName, id);
    }

    @Transactional(readOnly = true)
    public Object _load(String entityName, Serializable id, LockMode lockMode)
            throws DataAccessException {
        return getHibernateTemplate().load(entityName, id, lockMode);
    }

    @Transactional(readOnly = true)
    public <T> List<T> loadAll(Class<T> entityClass)
            throws DataAccessException {
        return getHibernateTemplate().loadAll(entityClass);
    }

    @Transactional(readOnly = true)
    public void _load(Object entity, Serializable id)
            throws DataAccessException {
        getHibernateTemplate().load(entity, id);
    }

    @Transactional(readOnly = true)
    public void refresh(Object entity)
            throws DataAccessException {
        getHibernateTemplate().refresh(entity);
    }

    @Transactional(readOnly = true)
    public void refresh(Object entity, LockMode lockMode)
            throws DataAccessException {
        getHibernateTemplate().refresh(entity, lockMode);
    }

    @Transactional
    public Serializable save(Object entity)
            throws DataAccessException {
        return getHibernateTemplate().save(entity);
    }

    @Transactional
    public Serializable save(String entityName, Object entity)
            throws DataAccessException {
        return getHibernateTemplate().save(entityName, entity);
    }

    @Transactional
    public void update(Object entity)
            throws DataAccessException {
        getHibernateTemplate().update(entity);
    }

    @Transactional
    public void update(Object entity, LockMode lockMode)
            throws DataAccessException {
        getHibernateTemplate().update(entity, lockMode);
    }

    @Transactional
    public void update(String entityName, Object entity)
            throws DataAccessException {
        getHibernateTemplate().update(entityName, entity);
    }

    @Transactional
    public void update(String entityName, Object entity, LockMode lockMode)
            throws DataAccessException {
        getHibernateTemplate().update(entityName, entity, lockMode);
    }

    @Transactional
    public void saveOrUpdate(Object entity)
            throws DataAccessException {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Transactional
    public void saveOrUpdate(String entityName, Object entity)
            throws DataAccessException {
        getHibernateTemplate().saveOrUpdate(entityName, entity);
    }

    @Transactional
    public void saveOrUpdateAll(IEntity<?>... entities)
            throws DataAccessException {
        saveOrUpdateAll(Arrays.asList(entities));
    }

    @Transactional
    public void saveOrUpdateAll(Collection<? extends IEntity<?>> entities)
            throws DataAccessException {

        HibernateTemplate template = getHibernateTemplate();

        try {
            template.saveOrUpdateAll(entities);
        } catch (DataAccessException e) {
            DataAccessExceptionUtil.dumpAndThrow(e);
        }

        if (evictImmediately) {
            for (Object entity : entities)
                template.evict(entity);
        }
    }

    @Transactional
    public void replicate(Object entity, ReplicationMode replicationMode)
            throws DataAccessException {
        getHibernateTemplate().replicate(entity, replicationMode);
    }

    @Transactional
    public void replicate(String entityName, Object entity, ReplicationMode replicationMode)
            throws DataAccessException {
        getHibernateTemplate().replicate(entityName, entity, replicationMode);
    }

    @Transactional
    public void persist(Object entity)
            throws DataAccessException {
        getHibernateTemplate().persist(entity);
    }

    @Transactional
    public void persist(String entityName, Object entity)
            throws DataAccessException {
        getHibernateTemplate().persist(entityName, entity);
    }

    @Transactional
    public <T> T merge(T entity)
            throws DataAccessException {
        return getHibernateTemplate().merge(entity);
    }

    @Transactional
    public <T> T merge(String entityName, T entity)
            throws DataAccessException {
        return getHibernateTemplate().merge(entityName, entity);
    }

    @Transactional
    public void delete(Object entity)
            throws DataAccessException {
        getHibernateTemplate().delete(entity);
    }

    @Transactional
    public void delete(Object entity, LockMode lockMode)
            throws DataAccessException {
        getHibernateTemplate().delete(entity, lockMode);
    }

    @Transactional
    public void delete(String entityName, Object entity)
            throws DataAccessException {
        getHibernateTemplate().delete(entityName, entity);
    }

    @Transactional
    public void delete(String entityName, Object entity, LockMode lockMode)
            throws DataAccessException {
        getHibernateTemplate().delete(entityName, entity, lockMode);
    }

    @Transactional
    public void deleteAll(Collection<? extends IEntity<?>> entities)
            throws DataAccessException {
        getHibernateTemplate().deleteAll(entities);
    }

    @Transactional
    public void deleteAll(IEntity<?>... entities)
            throws DataAccessException {
        getHibernateTemplate().deleteAll(Arrays.asList(entities));
    }

    @Transactional
    public int bulkUpdate(String queryString)
            throws DataAccessException {
        return getHibernateTemplate().bulkUpdate(queryString);
    }

    @Transactional
    public int bulkUpdate(String queryString, Object value)
            throws DataAccessException {
        return getHibernateTemplate().bulkUpdate(queryString, value);
    }

    @Transactional
    public int bulkUpdate(String queryString, Object... values)
            throws DataAccessException {
        return getHibernateTemplate().bulkUpdate(queryString, values);
    }

}
