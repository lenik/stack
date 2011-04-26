package com.bee32.plover.orm.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Filter;
import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateOperations;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.entity.IEntity;

@Service
@Lazy
@Transactional(readOnly = true)
public class CommonDataManager
        extends HibernateDaoSupport
        implements HibernateOperations {

    static Logger logger = LoggerFactory.getLogger(CommonDataManager.class);

    // XXX - how to not use dao-support?
    // SessionFactory sessionFactory;

    /**
     * Set <code>true</code> for debug purpose.
     */
    public boolean evictImmediately = false;

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

    /**
     * Return the persistent instance of the given entity class with the given identifier, throwing
     * an exception if not found.
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
    public <T> T fetch(Class<T> entityClass, Serializable id)
            throws DataAccessException {
        T entity = getHibernateTemplate().get(entityClass, id);
        if (entity == null)
            throw new ObjectRetrievalFailureException(entityClass.getName(), id);
        return entity;
    }

    @Transactional
    @Override
    // @Deprecated
    public void saveOrUpdateAll(Collection entities)
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

    // DELEGATES

    @Transactional
    @Override
    public void flush() {
        getHibernateTemplate().flush();
    }

    @Transactional
    @Override
    public <T> T execute(HibernateCallback<T> action)
            throws DataAccessException {
        return getHibernateTemplate().execute(action);
    }

    @Transactional
    @Override
    public List<?> executeFind(HibernateCallback<?> action)
            throws DataAccessException {
        return getHibernateTemplate().executeFind(action);
    }

    @Override
    public <T> T get(Class<T> entityClass, Serializable id)
            throws DataAccessException {
        return getHibernateTemplate().get(entityClass, id);
    }

    @Override
    public <T> T get(Class<T> entityClass, Serializable id, LockMode lockMode)
            throws DataAccessException {
        return getHibernateTemplate().get(entityClass, id, lockMode);
    }

    @Override
    public Object get(String entityName, Serializable id)
            throws DataAccessException {
        return getHibernateTemplate().get(entityName, id);
    }

    @Override
    public Object get(String entityName, Serializable id, LockMode lockMode)
            throws DataAccessException {
        return getHibernateTemplate().get(entityName, id, lockMode);
    }

    @Override
    public <T> T load(Class<T> entityClass, Serializable id)
            throws DataAccessException {
        return getHibernateTemplate().load(entityClass, id);
    }

    @Override
    public <T> T load(Class<T> entityClass, Serializable id, LockMode lockMode)
            throws DataAccessException {
        return getHibernateTemplate().load(entityClass, id, lockMode);
    }

    @Override
    public Object load(String entityName, Serializable id)
            throws DataAccessException {
        return getHibernateTemplate().load(entityName, id);
    }

    @Override
    public Object load(String entityName, Serializable id, LockMode lockMode)
            throws DataAccessException {
        return getHibernateTemplate().load(entityName, id, lockMode);
    }

    @Override
    public <T> List<T> loadAll(Class<T> entityClass)
            throws DataAccessException {
        return getHibernateTemplate().loadAll(entityClass);
    }

    @Override
    public void load(Object entity, Serializable id)
            throws DataAccessException {
        getHibernateTemplate().load(entity, id);
    }

    @Override
    public void refresh(Object entity)
            throws DataAccessException {
        getHibernateTemplate().refresh(entity);
    }

    @Override
    public void refresh(Object entity, LockMode lockMode)
            throws DataAccessException {
        getHibernateTemplate().refresh(entity, lockMode);
    }

    @Transactional
    @Override
    public Serializable save(Object entity)
            throws DataAccessException {
        return getHibernateTemplate().save(entity);
    }

    @Transactional
    @Override
    public Serializable save(String entityName, Object entity)
            throws DataAccessException {
        return getHibernateTemplate().save(entityName, entity);
    }

    @Transactional
    @Override
    public void update(Object entity)
            throws DataAccessException {
        getHibernateTemplate().update(entity);
    }

    @Transactional
    @Override
    public void update(Object entity, LockMode lockMode)
            throws DataAccessException {
        getHibernateTemplate().update(entity, lockMode);
    }

    @Transactional
    @Override
    public void update(String entityName, Object entity)
            throws DataAccessException {
        getHibernateTemplate().update(entityName, entity);
    }

    @Transactional
    @Override
    public void update(String entityName, Object entity, LockMode lockMode)
            throws DataAccessException {
        getHibernateTemplate().update(entityName, entity, lockMode);
    }

    @Transactional
    @Override
    public void saveOrUpdate(Object entity)
            throws DataAccessException {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Transactional
    @Override
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
    @Override
    public void replicate(Object entity, ReplicationMode replicationMode)
            throws DataAccessException {
        getHibernateTemplate().replicate(entity, replicationMode);
    }

    @Transactional
    @Override
    public void replicate(String entityName, Object entity, ReplicationMode replicationMode)
            throws DataAccessException {
        getHibernateTemplate().replicate(entityName, entity, replicationMode);
    }

    @Transactional
    @Override
    public void persist(Object entity)
            throws DataAccessException {
        getHibernateTemplate().persist(entity);
    }

    @Transactional
    @Override
    public void persist(String entityName, Object entity)
            throws DataAccessException {
        getHibernateTemplate().persist(entityName, entity);
    }

    @Transactional
    @Override
    public <T> T merge(T entity)
            throws DataAccessException {
        return getHibernateTemplate().merge(entity);
    }

    @Transactional
    @Override
    public <T> T merge(String entityName, T entity)
            throws DataAccessException {
        return getHibernateTemplate().merge(entityName, entity);
    }

    @Transactional
    @Override
    public void delete(Object entity)
            throws DataAccessException {
        getHibernateTemplate().delete(entity);
    }

    @Transactional
    @Override
    public void delete(Object entity, LockMode lockMode)
            throws DataAccessException {
        getHibernateTemplate().delete(entity, lockMode);
    }

    @Transactional
    @Override
    public void delete(String entityName, Object entity)
            throws DataAccessException {
        getHibernateTemplate().delete(entityName, entity);
    }

    @Transactional
    @Override
    public void delete(String entityName, Object entity, LockMode lockMode)
            throws DataAccessException {
        getHibernateTemplate().delete(entityName, entity, lockMode);
    }

    @Transactional
    @Override
    public void deleteAll(Collection entities)
            throws DataAccessException {
        getHibernateTemplate().deleteAll(entities);
    }

    @Transactional
    public void deleteAll(IEntity<?>... entities)
            throws DataAccessException {
        getHibernateTemplate().deleteAll(Arrays.asList(entities));
    }

    @Override
    @Transactional
    public int bulkUpdate(String queryString)
            throws DataAccessException {
        return getHibernateTemplate().bulkUpdate(queryString);
    }

    @Override
    @Transactional
    public int bulkUpdate(String queryString, Object value)
            throws DataAccessException {
        return getHibernateTemplate().bulkUpdate(queryString, value);
    }

    @Override
    @Transactional
    public int bulkUpdate(String queryString, Object... values)
            throws DataAccessException {
        return getHibernateTemplate().bulkUpdate(queryString, values);
    }

    @Override
    public boolean contains(Object entity)
            throws DataAccessException {
        return getHibernateTemplate().contains(entity);
    }

    @Override
    public void evict(Object entity)
            throws DataAccessException {
        getHibernateTemplate().evict(entity);
    }

    @Override
    public void initialize(Object proxy)
            throws DataAccessException {
        getHibernateTemplate().initialize(proxy);
    }

    @Override
    public Filter enableFilter(String filterName)
            throws IllegalStateException {
        return getHibernateTemplate().enableFilter(filterName);
    }

    @Override
    public void lock(Object entity, LockMode lockMode)
            throws DataAccessException {
        getHibernateTemplate().lock(entity, lockMode);
    }

    @Override
    public void lock(String entityName, Object entity, LockMode lockMode)
            throws DataAccessException {
        getHibernateTemplate().lock(entityName, entity, lockMode);
    }

    @Override
    public void clear()
            throws DataAccessException {
        getHibernateTemplate().clear();
    }

    @Override
    public List find(String queryString)
            throws DataAccessException {
        return getHibernateTemplate().find(queryString);
    }

    @Override
    public List find(String queryString, Object value)
            throws DataAccessException {
        return getHibernateTemplate().find(queryString, value);
    }

    @Override
    public List find(String queryString, Object... values)
            throws DataAccessException {
        return getHibernateTemplate().find(queryString, values);
    }

    @Override
    public List findByNamedParam(String queryString, String paramName, Object value)
            throws DataAccessException {
        return getHibernateTemplate().findByNamedParam(queryString, paramName, value);
    }

    @Override
    public List findByNamedParam(String queryString, String[] paramNames, Object[] values)
            throws DataAccessException {
        return getHibernateTemplate().findByNamedParam(queryString, paramNames, values);
    }

    @Override
    public List findByValueBean(String queryString, Object valueBean)
            throws DataAccessException {
        return getHibernateTemplate().findByValueBean(queryString, valueBean);
    }

    @Override
    public List findByNamedQuery(String queryName)
            throws DataAccessException {
        return getHibernateTemplate().findByNamedQuery(queryName);
    }

    @Override
    public List findByNamedQuery(String queryName, Object value)
            throws DataAccessException {
        return getHibernateTemplate().findByNamedQuery(queryName, value);
    }

    @Override
    public List findByNamedQuery(String queryName, Object... values)
            throws DataAccessException {
        return getHibernateTemplate().findByNamedQuery(queryName, values);
    }

    @Override
    public List findByNamedQueryAndNamedParam(String queryName, String paramName, Object value)
            throws DataAccessException {
        return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramName, value);
    }

    @Override
    public List findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values)
            throws DataAccessException {
        return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramNames, values);
    }

    @Override
    public List findByNamedQueryAndValueBean(String queryName, Object valueBean)
            throws DataAccessException {
        return getHibernateTemplate().findByNamedQueryAndValueBean(queryName, valueBean);
    }

    @Override
    public List findByCriteria(DetachedCriteria criteria)
            throws DataAccessException {
        return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public List findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults)
            throws DataAccessException {
        return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
    }

    @Override
    public List findByExample(Object exampleEntity)
            throws DataAccessException {
        return getHibernateTemplate().findByExample(exampleEntity);
    }

    @Override
    public List findByExample(String entityName, Object exampleEntity)
            throws DataAccessException {
        return getHibernateTemplate().findByExample(entityName, exampleEntity);
    }

    @Override
    public List findByExample(Object exampleEntity, int firstResult, int maxResults)
            throws DataAccessException {
        return getHibernateTemplate().findByExample(exampleEntity, firstResult, maxResults);
    }

    @Override
    public List findByExample(String entityName, Object exampleEntity, int firstResult, int maxResults)
            throws DataAccessException {
        return getHibernateTemplate().findByExample(entityName, exampleEntity, firstResult, maxResults);
    }

    @Override
    public Iterator iterate(String queryString)
            throws DataAccessException {
        return getHibernateTemplate().iterate(queryString);
    }

    @Override
    public Iterator iterate(String queryString, Object value)
            throws DataAccessException {
        return getHibernateTemplate().iterate(queryString, value);
    }

    @Override
    public Iterator iterate(String queryString, Object... values)
            throws DataAccessException {
        return getHibernateTemplate().iterate(queryString, values);
    }

    @Override
    public void closeIterator(Iterator it)
            throws DataAccessException {
        getHibernateTemplate().closeIterator(it);
    }

}
