package com.bee32.plover.thirdparty.hibernate.util;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;

import org.hibernate.Cache;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.TypeHelper;
import org.hibernate.classic.Session;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;

public abstract class DelegatedSessionFactory
        implements SessionFactory {

    private static final long serialVersionUID = 1L;

    protected abstract SessionFactory getDelegate();

    @Override
    public Reference getReference()
            throws NamingException {
        return getDelegate().getReference();
    }

    @Override
    public Session openSession(Connection connection) {
        return getDelegate().openSession(connection);
    }

    @Override
    public Session openSession(Interceptor interceptor)
            throws HibernateException {
        return getDelegate().openSession(interceptor);
    }

    @Override
    public Session openSession(Connection connection, Interceptor interceptor) {
        return getDelegate().openSession(connection, interceptor);
    }

    @Override
    public Session openSession()
            throws HibernateException {
        return getDelegate().openSession();
    }

    @Override
    public Session getCurrentSession()
            throws HibernateException {
        return getDelegate().getCurrentSession();
    }

    @Override
    public ClassMetadata getClassMetadata(Class persistentClass)
            throws HibernateException {
        return getDelegate().getClassMetadata(persistentClass);
    }

    @Override
    public ClassMetadata getClassMetadata(String entityName)
            throws HibernateException {
        return getDelegate().getClassMetadata(entityName);
    }

    @Override
    public CollectionMetadata getCollectionMetadata(String roleName)
            throws HibernateException {
        return getDelegate().getCollectionMetadata(roleName);
    }

    @Override
    public Map<String, ClassMetadata> getAllClassMetadata()
            throws HibernateException {
        return getDelegate().getAllClassMetadata();
    }

    @Override
    public Map getAllCollectionMetadata()
            throws HibernateException {
        return getDelegate().getAllCollectionMetadata();
    }

    @Override
    public Statistics getStatistics() {
        return getDelegate().getStatistics();
    }

    @Override
    public void close()
            throws HibernateException {
        getDelegate().close();
    }

    @Override
    public boolean isClosed() {
        return getDelegate().isClosed();
    }

    @Deprecated
    @Override
    public void evict(Class persistentClass)
            throws HibernateException {
        getDelegate().evict(persistentClass);
    }

    @Deprecated
    @Override
    public void evict(Class persistentClass, Serializable id)
            throws HibernateException {
        getDelegate().evict(persistentClass, id);
    }

    @Deprecated
    @Override
    public void evictEntity(String entityName)
            throws HibernateException {
        getDelegate().evictEntity(entityName);
    }

    @Deprecated
    @Override
    public void evictEntity(String entityName, Serializable id)
            throws HibernateException {
        getDelegate().evictEntity(entityName, id);
    }

    @Deprecated
    @Override
    public void evictCollection(String roleName)
            throws HibernateException {
        getDelegate().evictCollection(roleName);
    }

    @Deprecated
    @Override
    public void evictCollection(String roleName, Serializable id)
            throws HibernateException {
        getDelegate().evictCollection(roleName, id);
    }

    @Deprecated
    @Override
    public void evictQueries()
            throws HibernateException {
        getDelegate().evictQueries();
    }

    @Deprecated
    @Override
    public void evictQueries(String cacheRegion)
            throws HibernateException {
        getDelegate().evictQueries(cacheRegion);
    }

    @Override
    public StatelessSession openStatelessSession() {
        return getDelegate().openStatelessSession();
    }

    @Override
    public StatelessSession openStatelessSession(Connection connection) {
        return getDelegate().openStatelessSession(connection);
    }

    @Override
    public Set getDefinedFilterNames() {
        return getDelegate().getDefinedFilterNames();
    }

    @Override
    public FilterDefinition getFilterDefinition(String filterName)
            throws HibernateException {
        return getDelegate().getFilterDefinition(filterName);
    }

    @Override
    public Cache getCache() {
        return getDelegate().getCache();
    }

    @Override
    public boolean containsFetchProfileDefinition(String name) {
        return getDelegate().containsFetchProfileDefinition(name);
    }

    @Override
    public TypeHelper getTypeHelper() {
        return getDelegate().getTypeHelper();
    }

}
