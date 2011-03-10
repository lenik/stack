package com.bee32.plover.thirdparty.hibernate.util;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;

import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.classic.Session;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;

@SuppressWarnings("rawtypes")
public abstract class DelegatedSessionFactory
        implements SessionFactory {

    private static final long serialVersionUID = 1L;

    protected abstract SessionFactory getDelegate();

    public Reference getReference()
            throws NamingException {
        return getDelegate().getReference();
    }

    public Session openSession(Connection connection) {
        return getDelegate().openSession(connection);
    }

    public Session openSession(Interceptor interceptor)
            throws HibernateException {
        return getDelegate().openSession(interceptor);
    }

    public Session openSession(Connection connection, Interceptor interceptor) {
        return getDelegate().openSession(connection, interceptor);
    }

    public Session openSession()
            throws HibernateException {
        return getDelegate().openSession();
    }

    public Session getCurrentSession()
            throws HibernateException {
        return getDelegate().getCurrentSession();
    }

    public ClassMetadata getClassMetadata(Class persistentClass)
            throws HibernateException {
        return getDelegate().getClassMetadata(persistentClass);
    }

    public ClassMetadata getClassMetadata(String entityName)
            throws HibernateException {
        return getDelegate().getClassMetadata(entityName);
    }

    public CollectionMetadata getCollectionMetadata(String roleName)
            throws HibernateException {
        return getDelegate().getCollectionMetadata(roleName);
    }

    public Map getAllClassMetadata()
            throws HibernateException {
        return getDelegate().getAllClassMetadata();
    }

    public Map getAllCollectionMetadata()
            throws HibernateException {
        return getDelegate().getAllCollectionMetadata();
    }

    public Statistics getStatistics() {
        return getDelegate().getStatistics();
    }

    public void close()
            throws HibernateException {
        getDelegate().close();
    }

    public boolean isClosed() {
        return getDelegate().isClosed();
    }

    public void evict(Class persistentClass)
            throws HibernateException {
        getDelegate().evict(persistentClass);
    }

    public void evict(Class persistentClass, Serializable id)
            throws HibernateException {
        getDelegate().evict(persistentClass, id);
    }

    public void evictEntity(String entityName)
            throws HibernateException {
        getDelegate().evictEntity(entityName);
    }

    public void evictEntity(String entityName, Serializable id)
            throws HibernateException {
        getDelegate().evictEntity(entityName, id);
    }

    public void evictCollection(String roleName)
            throws HibernateException {
        getDelegate().evictCollection(roleName);
    }

    public void evictCollection(String roleName, Serializable id)
            throws HibernateException {
        getDelegate().evictCollection(roleName, id);
    }

    public void evictQueries()
            throws HibernateException {
        getDelegate().evictQueries();
    }

    public void evictQueries(String cacheRegion)
            throws HibernateException {
        getDelegate().evictQueries(cacheRegion);
    }

    public StatelessSession openStatelessSession() {
        return getDelegate().openStatelessSession();
    }

    public StatelessSession openStatelessSession(Connection connection) {
        return getDelegate().openStatelessSession(connection);
    }

    public Set getDefinedFilterNames() {
        return getDelegate().getDefinedFilterNames();
    }

    public FilterDefinition getFilterDefinition(String filterName)
            throws HibernateException {
        return getDelegate().getFilterDefinition(filterName);
    }

}
