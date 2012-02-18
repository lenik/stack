package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.free.NotImplementedException;
import javax.inject.Inject;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.bee32.plover.criteria.hibernate.AvgProjection;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.MaxProjection;
import com.bee32.plover.criteria.hibernate.MinProjection;
import com.bee32.plover.criteria.hibernate.ProjectionElement;
import com.bee32.plover.criteria.hibernate.SumProjection;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.dao.HibernateDaoSupportUtil;
import com.bee32.plover.orm.dao.HibernateTemplate;
import com.bee32.plover.site.scope.PerSite;

/**
 * &#64;Wireable is not inheritable, so you should add Configuration annotation in all concrete
 * Module classes, to enable Spring usage.
 */
@ComponentTemplate
@PerSite
@SuppressWarnings("deprecation")
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
        List<E> list = (List<E>) getHibernateTemplate().loadAll(entityType);
        return postLoadDecorated(list);
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

        postLoad(entity);

        return entity;
    }

    @Override
    public E lazyLoad(K id) {
        if (id == null)
            throw new NullPointerException("id");
        E entity = getHibernateTemplate().load(entityType, id);

        try {
            postLoad(entity);
        } catch (ObjectRetrievalFailureException e) {
            // XXX entity maybe not existed.
            // But should it be this exception type?
            throw e;
        }
        return entity;
    }

    @Override
    public E getOrFail(K id) {
        E entity = get(id);
        if (entity == null)
            throw new ObjectRetrievalFailureException(entityType, id);

        return entity;
    }

    /**
     * @throws DataAccessException
     *             in case of Hibernate errors.
     */
    @Override
    public K save(E entity) {
        preSave(entity);

        HibernateTemplate template = getHibernateTemplate();
        Serializable key = template.save(entity);
        return keyType.cast(key);
    }

    @Override
    public void saveAll(Collection<? extends E> entities) {
        if (entities == null)
            throw new NullPointerException("objects");

        for (E entity : entities)
            preSave(entity);

        // Need transactional wrapper.
        {
            HibernateTemplate template = getHibernateTemplate();
            // for (E entity : entities)
            // template.save(entity);

            template.saveOrUpdateAll(entities);
        }
    }

    @Override
    public void saveOrUpdateAll(Collection<? extends E> entities) {
        if (entities == null)
            throw new NullPointerException("entities");

        for (E entity : entities)
            preSave(entity);

        // Need transactional wrapper.
        getHibernateTemplate().saveOrUpdateAll(entities);
    }

    /**
     * @throws DataAccessException
     *             If entity with the same natural id was existed, or other hibernate errors.
     */
    @Override
    public void saveByNaturalId(E entity) {
        ICriteriaElement selector = entity.getSelector();
        E first = getFirst(selector);
        if (first != null)
            throw new DataIntegrityViolationException("Already existed: natural id = " + entity.getNaturalId());
        getHibernateTemplate().save(entity);
    }

    @Override
    public void saveOrUpdateByNaturalId(E entity) {
        ICriteriaElement selector = entity.getSelector();
        E first = getFirst(selector);
        if (first != null) {
            @SuppressWarnings("unchecked")
            Entity<Serializable> _entity = (Entity<Serializable>) entity;
            EntityAccessor.setId(_entity, first.getId());
            evict(first);
        }
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public void update(E entity) {
        preSave(entity);
        getHibernateTemplate().update(entity);
    }

    @Override
    public void refresh(E entity) {
        getHibernateTemplate().refresh(entity);
    }

    public void saveOrUpdate(E entity) {
        preSave(entity);
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public boolean delete(Object _entity) {
        @SuppressWarnings("unchecked")
        E entity = (E) _entity;

        preDelete(entity);
        getHibernateTemplate().delete(entity);
        // count??
        return true;
    }

    @Override
    public final boolean deleteByKey(K key) {
        return deleteById(key);
    }

    @Override
    public void deleteAll() {
        HibernateTemplate template = getHibernateTemplate();
        // String hql = "delete from " + entityType.getSimpleName();
        // template.bulkUpdate(hql);
        List<? extends E> list = template.loadAll(entityType);
        for (E e : list)
            preDelete(e);

        template.deleteAll(list);
    }

    @Override
    public int deleteAll(Collection<?> entities) {
        HibernateTemplate template = getHibernateTemplate();
        // String hql = "delete from " + entityType.getSimpleName();
        // template.bulkUpdate(hql);
        for (Object obj : entities) {
            @SuppressWarnings("unchecked")
            E entity = (E) obj;
            preDelete(entity);
        }

        template.deleteAll(entities);
        return -1;
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
        E entity = getHibernateTemplate().get(entityType, key, lockMode);

        if (entity != null)
            postLoad(entity);

        return entity;
    }

    @Override
    public void update(E entity, LockMode lockMode)
            throws DataAccessException {
        if (entity == null)
            throw new NullPointerException("entity");

        preSave(entity);

        getHibernateTemplate().update(entity, lockMode);
    }

    @Override
    public boolean delete(Object _entity, LockMode lockMode)
            throws DataAccessException {
        @SuppressWarnings("unchecked")
        E entity = (E) _entity;

        preDelete(entity);

        getHibernateTemplate().delete(entity, lockMode);
        return true;
    }

    @Override
    public int deleteAll(Collection<?> entities, LockMode lockMode)
            throws DataAccessException {
        return deleteAll(entities);
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

    final Criteria createCriteria(int options, ICriteriaElement... criteriaElements) {
        Criteria criteria = getSession().createCriteria(entityType);
        if (criteriaElements != null)
            for (ICriteriaElement elm : criteriaElements) {
                if (elm == null)
                    continue;
                elm.apply(criteria, options);

                Criterion criterion = elm.getCriterion(options);
                if (criterion != null)
                    criteria.add(criterion);
            }
        return criteria;
    }

    @Deprecated
    final String toClause(ICriteriaElement... criteriaElements) {
        // Session session = getSession();
        // Criteria criteria = session.createCriteria(entityType);
        Conjunction conj = Restrictions.conjunction();
        for (ICriteriaElement e : criteriaElements) {
            Criterion criterion = e.getCriterion(0);
            if (criterion == null)
                continue;
            conj.add(criterion);
        }
        throw new NotImplementedException("Couldn't obtain a CriteriaQuery");
    }

    @Override
    public E getUnique(ICriteriaElement... criteriaElements)
            throws HibernateException {
        Criteria _criteria = createCriteria(0, criteriaElements);
        E entity;
        try {
            entity = (E) _criteria.uniqueResult();
        } catch (HibernateException e) {
            throw new NonUniqueException(e);
        }

        if (entity != null)
            postLoad(entity);

        return entity;
    }

    @Override
    public E getFirst(ICriteriaElement... criteriaE) {
        Criteria criteria = createCriteria(0, criteriaE);
        criteria.setMaxResults(1);
        List<E> list = criteria.list();
        if (list.isEmpty())
            return null;

        E first = list.get(0);
        postLoad(first);

        return first;
    }

    @Override
    public final E getByName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        return getFirst(new Equals("name", name));
    }

    @Override
    public List<E> list(ICriteriaElement... criteriaElements) {
        Criteria criteria = createCriteria(0, criteriaElements);
        List<E> list = criteria.list();
        return postLoadDecorated(list);
    }

    @Override
    public <T> List<T> listMisc(ICriteriaElement... criteriaElements) {
        Criteria criteria = createCriteria(0, criteriaElements);
        List<T> list = criteria.list();
        return list;
    }

    @Override
    public <T> T getMisc(ICriteriaElement... criteriaElements) {
        Criteria criteria = createCriteria(0, criteriaElements);
        List<T> list = criteria.list();
        if (list.isEmpty())
            return null;
        else
            return list.get(0);
    }

    <T> T getMisc(ProjectionElement projectionElement, ICriteriaElement... criteriaElements) {
        ICriteriaElement[] cat = new ICriteriaElement[criteriaElements.length + 1];
        cat[0] = projectionElement;
        System.arraycopy(criteriaElements, 0, cat, 1, criteriaElements.length);
        return getMisc(cat);
    }

    @Override
    public <T extends Number> T sum(String propertyName, ICriteriaElement... criteriaElements) {
        return getMisc(new SumProjection(propertyName), criteriaElements);
    }

    @Override
    public <T extends Number> T average(String propertyName, ICriteriaElement... criteriaElements) {
        return getMisc(new AvgProjection(propertyName), criteriaElements);
    }

    @Override
    public <T extends Number> T min(String propertyName, ICriteriaElement... criteriaElements) {
        return getMisc(new MinProjection(propertyName), criteriaElements);
    }

    @Override
    public <T extends Number> T max(String propertyName, ICriteriaElement... criteriaElements) {
        return getMisc(new MaxProjection(propertyName), criteriaElements);
    }

    @Override
    public int count(ICriteriaElement... criteriaElements) {
        Criteria criteria = createCriteria(//
                ICriteriaElement.OPTIM_COUNT /* | ICriteriaElement.NO_PROJECTION */, //
                criteriaElements);
        criteria.setProjection(Projections.rowCount());

        Object result = criteria.uniqueResult();
        if (result == null)
            return 0;
        // throw new UnexpectedException("Count() returns null");

        return ((Number) result).intValue();
    }

    @Override
    public boolean deleteById(K id) {
        HibernateTemplate template = getHibernateTemplate();
        E entity = template.get(getEntityType(), id);
        if (entity == null) {
            logger.debug("Entity isn't existed: " + getEntityType() + " id=" + id);
            return false;
        }

        preDelete(entity);

        template.delete(entity);
        return true;
    }

    @Override
    public int findAndDelete(ICriteriaElement... criteriaElements) {
        HibernateTemplate template = getHibernateTemplate();

        Criteria criteria = createCriteria(ICriteriaElement.NO_ORDER, criteriaElements);
        List<E> list = criteria.list();

        for (E e : list)
            preDelete(e);

        template.deleteAll(list);

        // approx.
        return list.size();
    }

    /**
     * <pre>
     * 3. Extensions.
     * </pre>
     */

    /**
     * Post loading.
     *
     * Please be aware of lazy-init entity.
     *
     * @see PostLoad
     */
    @Deprecated
    protected void postLoad(E entity) {
    }

    protected final <T> List<T> postLoadDecorated(List<T> list) {
        return list;
    }

    /**
     * Inject redundant attributes to the entity before save
     *
     * @see PrePersist
     * @see PreUpdate
     * @see PostPersist
     * @see PostUpdate
     */
    @Deprecated
    protected void preSave(E entity) {
    }

    /**
     * Update references (like depth) before delete.
     *
     * @see PreRemove
     * @see PostRemove
     */
    @Deprecated
    protected void preDelete(E entity) {
    }

}
