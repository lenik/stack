package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.bee32.plover.orm.dao.HibernateDaoSupport;
import com.bee32.plover.orm.dao.HibernateTemplate;

public class HibernateEntityRepository<E extends IEntity<K>, K>
        extends EntityRepository<E, K> {

    static class Support
            extends HibernateDaoSupport {

        Session _getSession() {
            return super.getSession();
        }

        Session _getSession(boolean allowCreate) {
            return super.getSession(allowCreate);
        }

    }

    private Support support = new Support();

    public HibernateEntityRepository(Class<E> entityType, Class<K> keyType) {
        super(entityType, keyType);
    }

    public HibernateEntityRepository(Class<E> instanceType, Class<? extends E> entityType, Class<K> keyType) {
        super(instanceType, entityType, keyType);
    }

    public HibernateEntityRepository(String name, Class<E> instanceType, Class<? extends E> entityType, Class<K> keyType) {
        super(name, instanceType, entityType, keyType);
    }

    public HibernateEntityRepository(String name, Class<E> instanceType, Class<K> keyType) {
        super(name, instanceType, keyType);
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
    public List<? extends E> list() {
        return getHibernateTemplate().loadAll(entityType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<? extends K> listKeys() {
        List<?> _list = getHibernateTemplate().find("select ID from " + entityType.getName());
        return (List<? extends K>) _list;
    }

    @Override
    public boolean contains(Object entity) {
        return getHibernateTemplate().contains(entity);
    }

    @Override
    public boolean containsKey(Object key) {
        Serializable id = (Serializable) key;
        E entity = getHibernateTemplate().get(entityType, id);
        return entity != null;
    }

    @Override
    public E retrieve(Object key) {
        Serializable id = (Serializable) key;
        E entity = getHibernateTemplate().get(entityType, id);
        return entity;
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

    @Override
    public void refresh(E entity) {
        getHibernateTemplate().refresh(entity);
    }

    @Override
    public void delete(Object entity) {
        getHibernateTemplate().delete(entity);
    }

    @Override
    public void deleteByKey(Object key) {
        E entity = retrieve(key);
        delete(entity);
    }

    @Override
    public void deleteAll() {
        HibernateTemplate template = getHibernateTemplate();
        List<? extends E> list = template.loadAll(entityType);
        template.deleteAll(list);
    }

}
