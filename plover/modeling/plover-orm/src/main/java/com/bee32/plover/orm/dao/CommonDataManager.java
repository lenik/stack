package com.bee32.plover.orm.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;
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
    private boolean evictImmediately = true;

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
    public void saveAll(String entityName, Collection<?> entities) {
        HibernateTemplate template = getHibernateTemplate();
        // HibernateTemplate getHibernateTemplate() = new HibernateTemplate(sessionFactory);

        for (Object entity : entities)
            if (entityName == null)
                template.save(entity);
            else
                template.save(entityName, entity);

        try {
            template.flush();
        } catch (DataAccessException e) {
            Throwable spec = e.getMostSpecificCause();
            if (spec instanceof SQLException) {
                SQLException sqle = (SQLException) spec;
                SQLException next;
                while ((next = sqle.getNextException()) != null) {
                    System.err.println("The next exception in " + sqle);
                    sqle.printStackTrace();
                    sqle = next;
                }
            }
            Throwable root = e.getRootCause();
            root.printStackTrace();
        }

        if (evictImmediately) {
            for (Object entity : entities)
                template.evict(entity);
        }
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

    @Transactional
    public <T> T load(Class<T> entityClass, Serializable id)
            throws DataAccessException {
        return getHibernateTemplate().load(entityClass, id);
    }

    @Transactional
    public <T> T load(Class<T> entityClass, Serializable id, LockMode lockMode)
            throws DataAccessException {
        return getHibernateTemplate().load(entityClass, id, lockMode);
    }

    @Transactional
    public Object load(String entityName, Serializable id)
            throws DataAccessException {
        return getHibernateTemplate().load(entityName, id);
    }

    @Transactional
    public Object load(String entityName, Serializable id, LockMode lockMode)
            throws DataAccessException {
        return getHibernateTemplate().load(entityName, id, lockMode);
    }

    @Transactional
    public <T> List<T> loadAll(Class<T> entityClass)
            throws DataAccessException {
        return getHibernateTemplate().loadAll(entityClass);
    }

    @Transactional
    public void load(Object entity, Serializable id)
            throws DataAccessException {
        getHibernateTemplate().load(entity, id);
    }

    @Transactional
    public void refresh(Object entity)
            throws DataAccessException {
        getHibernateTemplate().refresh(entity);
    }

    @Transactional
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
    public void saveOrUpdateAll(Collection<? extends IEntity<?>> entities)
            throws DataAccessException {
        getHibernateTemplate().saveOrUpdateAll(entities);
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
