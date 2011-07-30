package com.bee32.plover.orm.dao;

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

@Deprecated
public class MemdbSessionFactory
        implements SessionFactory {

    private static final long serialVersionUID = 1L;

    final MemTable memdb;

    public MemdbSessionFactory(MemTable memdb) {
        if (memdb == null)
            throw new NullPointerException("memdb");
        this.memdb = memdb;
    }

    @Override
    public Reference getReference()
            throws NamingException {
        return null;
    }

    @Override
    public Session openSession(Connection connection) {
        return null;
    }

    @Override
    public Session openSession(Interceptor interceptor)
            throws HibernateException {
        return null;
    }

    @Override
    public Session openSession(Connection connection, Interceptor interceptor) {
        return null;
    }

    @Override
    public Session openSession()
            throws HibernateException {
        return null;
    }

    @Override
    public Session getCurrentSession()
            throws HibernateException {
        return null;
    }

    @Override
    public ClassMetadata getClassMetadata(Class persistentClass)
            throws HibernateException {
        return null;
    }

    @Override
    public ClassMetadata getClassMetadata(String entityName)
            throws HibernateException {
        return null;
    }

    @Override
    public CollectionMetadata getCollectionMetadata(String roleName)
            throws HibernateException {
        return null;
    }

    @Override
    public Map getAllClassMetadata()
            throws HibernateException {
        return null;
    }

    @Override
    public Map getAllCollectionMetadata()
            throws HibernateException {
        return null;
    }

    @Override
    public Statistics getStatistics() {
        return null;
    }

    @Override
    public void close()
            throws HibernateException {
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void evict(Class persistentClass)
            throws HibernateException {
    }

    @Override
    public void evict(Class persistentClass, Serializable id)
            throws HibernateException {
    }

    @Override
    public void evictEntity(String entityName)
            throws HibernateException {
    }

    @Override
    public void evictEntity(String entityName, Serializable id)
            throws HibernateException {
    }

    @Override
    public void evictCollection(String roleName)
            throws HibernateException {
    }

    @Override
    public void evictCollection(String roleName, Serializable id)
            throws HibernateException {
    }

    @Override
    public void evictQueries()
            throws HibernateException {
    }

    @Override
    public void evictQueries(String cacheRegion)
            throws HibernateException {
    }

    @Override
    public StatelessSession openStatelessSession() {
        return null;
    }

    @Override
    public StatelessSession openStatelessSession(Connection connection) {
        return null;
    }

    @Override
    public Set getDefinedFilterNames() {
        return null;
    }

    @Override
    public FilterDefinition getFilterDefinition(String filterName)
            throws HibernateException {
        return null;
    }

}
