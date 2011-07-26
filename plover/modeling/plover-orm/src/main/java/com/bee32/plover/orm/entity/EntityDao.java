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
import org.hibernate.criterion.Projections;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.dao.HibernateDaoSupportUtil;
import com.bee32.plover.orm.dao.HibernateTemplate;

/**
 * &#64;Wireable is not inheritable, so you should add Configuration annotation in all concrete
 * Module classes, to enable Spring usage.
 */
@ComponentTemplate
@Lazy
public abstract class EntityDao<E extends Entity<? extends K>, K extends Serializable>
        extends EntityRepository<E, K>
        implements IEntityAccessService<E, K> {

    public EntityDao() {
        super();
    }

    public EntityDao(String name) {
        super(name);
    }

    public EntityDao(Class<E> entityType, Class<K> keyType) {
        super(entityType, keyType);
    }

    public EntityDao(String name, Class<E> entityType, Class<K> keyType) {
        super(name, entityType, keyType);
    }

    /**
     * <pre>
     * 0. Hibernate utils.
     * -----------------------------------------------------------------------
     *    getHibernateTemplate() need HibernateDaoHibernate to work.
     * </pre>
     */

    private HibernateDaoSupportUtil support = new HibernateDaoSupportUtil();

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

    /**
     * <pre>
     * 1. IRepository
     * -----------------------------------------------------------------------
     *      The implementation for the base IRepository.
     *
     *      These methods are common for both core repository and entity-repository.
     * </pre>
     */

    /** {@inheritDoc} */
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
    public boolean containsKey(Object _key) {
        K key = keyType.cast(_key);
        E entity = getHibernateTemplate().get(entityType, key);
        return entity != null;
    }

    @Override
    public E get(K key) {
        E entity = getHibernateTemplate().get(entityType, key);

        if (entity == null)
            return null;

        // post-load?

        return entity;
    }

    @Override
    public E lazyLoad(K id) {
        E entity = getHibernateTemplate().load(entityType, id);
        return entity;
    }

    @Override
    public E getOrFail(K id) {
        E entity = get(id);
        if (entity == null)
            throw new ObjectRetrievalFailureException(entityType, id);
        return entity;
    }

    @Override
    public K save(E entity) {
        Serializable key = getHibernateTemplate().save(entity);
        // XXX - convert serializable to K.
        return keyType.cast(key);
    }

    @Override
    public void saveAll(Collection<? extends E> objects) {
        // Need transactional wrapper.
        getHibernateTemplate().saveOrUpdateAll(objects);
    }

    @Override
    public void saveOrUpdateAll(Collection<? extends E> entities) {
        // Need transactional wrapper.
        getHibernateTemplate().saveOrUpdateAll(entities);
    }

    @Override
    public void update(E entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void refresh(E entity) {
        getHibernateTemplate().refresh(entity);
    }

    public void saveOrUpdate(E entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public boolean delete(Object entity) {
        getHibernateTemplate().delete(entity);
        // count??
        return true;
    }

    @Override
    public boolean deleteByKey(K key) {
        return deleteById(key);
    }

    @Override
    public void deleteAll() {
        HibernateTemplate template = getHibernateTemplate();
        template.bulkUpdate("delete from " + entityType.getSimpleName());
        // List<? extends E> list = template.loadAll(entityType);
        // template.deleteAll(list);
    }

    @Override
    public int count() {
        return count((ICriteriaElement[]) null);
    }

    /**
     * <pre>
     * 2. IEntityRepo_H
     * -----------------------------------------------------------------------
     *      The base implementation for Hibernate entity daos.
     * </pre>
     */

    @Override
    public E retrieve(K key, LockMode lockMode)
            throws DataAccessException {
        return getHibernateTemplate().get(entityType, key, lockMode);
    }

    @Override
    public void update(E entity, LockMode lockMode)
            throws DataAccessException {
        getHibernateTemplate().update(entity, lockMode);
    }

    @Override
    public boolean delete(Object entity, LockMode lockMode)
            throws DataAccessException {
        getHibernateTemplate().delete(entity, lockMode);
        return true;
    }

    @Override
    public E merge(E entity)
            throws DataAccessException {
        HibernateTemplate template = getHibernateTemplate();
        E merged = template.merge(entity);
        return merged;
    }

    @Override
    public void evict(E entity)
            throws DataAccessException {
        HibernateTemplate template = getHibernateTemplate();
        template.evict(entity);
    }

    @Override
    public void replicate(E entity, ReplicationMode replicationMode)
            throws DataAccessException {
        getHibernateTemplate().replicate(entity, replicationMode);
    }

    @Override
    public void flush() {
        getHibernateTemplate().flush();
    }

    // IEntityManager

    final Criteria createCriteria(ICriteriaElement... criteriaElements) {
        Criteria criteria = getSession().createCriteria(entityType);
        if (criteriaElements != null)
            for (ICriteriaElement elm : criteriaElements) {
                if (elm == null)
                    continue;
                elm.apply(criteria);
            }
        return criteria;
    }

    @Override
    public E getUnique(ICriteriaElement... criteriaElements) {
        Criteria _criteria = createCriteria(criteriaElements);
        E result = (E) _criteria.uniqueResult();
        return result;
    }

    @Override
    public E getFirst(ICriteriaElement... criteriaE) {
        Criteria criteria = createCriteria(criteriaE);
        criteria.setMaxResults(1);
        List<E> list = criteria.list();
        if (list.isEmpty())
            return null;
        else
            return list.get(0);
    }

    @Override
    public E getByName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        return getFirst(new Equals("name", name));
    }

    @Override
    public List<E> list(ICriteriaElement... criteriaE) {
        Criteria criteria = createCriteria(criteriaE);
        List<E> list = criteria.list();
        return list;
    }

    @Override
    public int count(ICriteriaElement... criteriaE) {
        Criteria criteria = createCriteria(criteriaE);
        criteria.setProjection(Projections.rowCount());

        Object result = criteria.uniqueResult();
        if (result == null)
            throw new UnexpectedException("Count() returns null");

        return ((Number) result).intValue();
    }

    @Override
    public boolean deleteById(K id) {
        String entityName = getEntityType().getSimpleName();
        String hql = "delete from " + entityName + " where id=?";
        int rows = getHibernateTemplate().bulkUpdate(hql, id);
        return rows != 0;
    }

    @Override
    public void deleteAll(ICriteriaElement... criteriaE) {
        Criteria criteria = createCriteria(criteriaE);
        List<E> list = criteria.list();
        getHibernateTemplate().deleteAll(list);
    }

    /**
     * <pre>
     * 3. Extensions.
     * </pre>
     */
    public void postLoad(E entity) {

    }

    public void preSave(E entity) {

    }

}
