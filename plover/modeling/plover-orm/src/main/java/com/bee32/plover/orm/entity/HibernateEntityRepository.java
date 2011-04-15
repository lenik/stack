package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.free.UnexpectedException;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.springframework.dao.DataAccessException;

import com.bee32.plover.orm.dao.HibernateDaoSupportUtil;
import com.bee32.plover.orm.dao.HibernateTemplate;

public class HibernateEntityRepository<E extends IEntity<K>, K extends Serializable>
        extends EntityRepository<E, K> {

    private HibernateDaoSupportUtil support = new HibernateDaoSupportUtil();

    public HibernateEntityRepository() {
        super();
    }

    public HibernateEntityRepository(String name) {
        super(name);
    }

    @Inject
    public final void setSessionFactory(SessionFactory sessionFactory) {
        support.setSessionFactory(sessionFactory);
    }

    public final SessionFactory getSessionFactory() {
        return support.getSessionFactory();
    }

    public final void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        support.setHibernateTemplate(hibernateTemplate);
    }

    public final HibernateTemplate getHibernateTemplate() {
        return support.getHibernateTemplateEx();
    }

    protected final Session getSession() {
        return support._getSession();
    }

    protected final Session getSession(boolean allowCreate) {
        return support._getSession(allowCreate);
    }

    @Override
    public List<E> list() {
        return (List<E>) getHibernateTemplate().loadAll(entityType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<K> keys() {
        List<?> _list = getHibernateTemplate().find("select id from " + entityType.getName());
        return (Collection<K>) _list;
    }

    @Override
    public boolean contains(Object entity) {
        return getHibernateTemplate().contains(entity);
    }

    @Override
    public boolean containsKey(Serializable key) {
        E entity = getHibernateTemplate().get(entityType, key);
        return entity != null;
    }

    @Override
    public E get(K key) {
        E entity = getHibernateTemplate().get(entityType, key);
        return entity;
    }

    public E retrieve(K key, LockMode lockMode)
            throws DataAccessException {
        return getHibernateTemplate().get(entityType, key, lockMode);
    }

    @Override
    public K save(E entity) {
        Serializable key = getHibernateTemplate().save(entity);
        // TODO - convert serializable to K.
        return keyType.cast(key);
    }

    @Override
    public void update(E entity) {
        getHibernateTemplate().update(entity);
    }

    public void update(E entity, LockMode lockMode)
            throws DataAccessException {
        getHibernateTemplate().update(entity, lockMode);
    }

    @Override
    public void refresh(E entity) {
        getHibernateTemplate().refresh(entity);
    }

    public void saveOrUpdate(E entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public void delete(Object entity) {
        getHibernateTemplate().delete(entity);
    }

    public void delete(Object entity, LockMode lockMode)
            throws DataAccessException {
        getHibernateTemplate().delete(entity, lockMode);
    }

    @Override
    public void deleteByKey(K key) {
        E entity = get(key);
        delete(entity);
    }

    @Override
    public void deleteAll() {
        HibernateTemplate template = getHibernateTemplate();
        template.bulkUpdate("delete from " + entityType.getSimpleName());

// List<? extends E> list = template.loadAll(entityType);
// template.deleteAll(list);
    }

    public void merge(E entity)
            throws DataAccessException {
        HibernateTemplate template = getHibernateTemplate();
        template.merge(entity);
    }

    public void evict(E entity)
            throws DataAccessException {
        HibernateTemplate template = getHibernateTemplate();
        template.evict(entity);
    }

    public void replicate(E entity, ReplicationMode replicationMode)
            throws DataAccessException {
        getHibernateTemplate().replicate(entity, replicationMode);
    }

    public void flush() {
        getHibernateTemplate().flush();
    }

    @Override
    public long count() {
        return count((Criterion[]) null);
    }

    public long count(Criterion... restrictions) {
        Criteria criteria = getSession().createCriteria(entityType);

        if (restrictions != null)
            for (Criterion restriction : restrictions)
                criteria.add(restriction);

        criteria.setProjection(Projections.rowCount());

        Object result = criteria.uniqueResult();

        if (result == null)
            throw new UnexpectedException("Count() returns null");

        return (Long) result;
    }

}
