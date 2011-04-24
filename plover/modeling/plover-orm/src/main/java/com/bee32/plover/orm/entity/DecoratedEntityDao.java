package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.free.ParseException;
import javax.servlet.ServletRequest;

import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.dao.DataAccessException;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.naming.INamedNode;
import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.arch.util.ExceptionSupport;
import com.bee32.plover.arch.util.IStruct;
import com.bee32.plover.orm.dao.HibernateTemplate;

public class DecoratedEntityDao<E extends EntityBean<K>, K extends Serializable> {

    private final EntityDao<E, K> impl;

    public DecoratedEntityDao(EntityDao<E, K> impl) {
        if (impl == null)
            throw new NullPointerException("impl");
        this.impl = impl;
    }

    public K convertRefNameToKey(String refName)
            throws ParseException {
        return impl.convertRefNameToKey(refName);
    }

    public Class<? extends E> getEntityType() {
        return impl.getEntityType();
    }

    public final void setSessionFactory(SessionFactory sessionFactory) {
        impl.setSessionFactory(sessionFactory);
    }

    public String getName() {
        return impl.getName();
    }

    public final SessionFactory getSessionFactory() {
        return impl.getSessionFactory();
    }

    public IAppearance getAppearance() {
        return impl.getAppearance();
    }

    public final void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        impl.setHibernateTemplate(hibernateTemplate);
    }

    public String convertKeyToRefName(K key) {
        return impl.convertKeyToRefName(key);
    }

    public final HibernateTemplate getHibernateTemplate() {
        return impl.getHibernateTemplate();
    }

    public String refName() {
        return impl.refName();
    }

    public int getPriority() {
        return impl.getPriority();
    }

    public INamedNode getParent() {
        return impl.getParent();
    }

    public E load(K key) {
        return impl.load(key);
    }

    public void setParent(INamedNode parentLocator) {
        impl.setParent(parentLocator);
    }

    public List<E> list() {
        return impl.list();
    }

    public Class<?> getChildType() {
        return impl.getChildType();
    }

    public Collection<K> keys() {
        return impl.keys();
    }

    public Object getChild(String location) {
        return impl.getChild(location);
    }

    public boolean contains(Object entity) {
        return impl.contains(entity);
    }

    public boolean containsKey(Object _key) {
        return impl.containsKey(_key);
    }

    public boolean hasChild(Object obj) {
        return impl.hasChild(obj);
    }

    public ExceptionSupport getExceptionSupport() {
        return impl.getExceptionSupport();
    }

    public E get(K key) {
        return impl.get(key);
    }

    public String getChildName(Object obj) {
        return impl.getChildName(obj);
    }

    public E retrieve(K key, LockMode lockMode)
            throws DataAccessException {
        return impl.retrieve(key, lockMode);
    }

    public E populate(ServletRequest request)
            throws BuildException {
        return impl.populate(request);
    }

    public Collection<String> getChildNames() {
        return impl.getChildNames();
    }

    public K save(E entity) {
        return impl.save(entity);
    }

    public boolean equals(Object obj) {
        return impl.equals(obj);
    }

    public void saveOrUpdateAll(Collection<? extends E> entities) {
        impl.saveOrUpdateAll(entities);
    }

    public boolean populate(E obj, IStruct struct)
            throws BuildException {
        return impl.populate(obj, struct);
    }

    public K getKey(E entity) {
        return impl.getKey(entity);
    }

    public void update(E entity) {
        impl.update(entity);
    }

    public Iterable<?> getChildren() {
        return impl.getChildren();
    }

    public void update(E entity, LockMode lockMode)
            throws DataAccessException {
        impl.update(entity, lockMode);
    }

    public void refresh(E entity) {
        impl.refresh(entity);
    }

    public E populate(IStruct struct)
            throws BuildException {
        return impl.populate(struct);
    }

    public void saveOrUpdate(E entity) {
        impl.saveOrUpdate(entity);
    }

    public void delete(Object entity) {
        impl.delete(entity);
    }

    public int hashCode() {
        return impl.hashCode();
    }

    public void delete(Object entity, LockMode lockMode)
            throws DataAccessException {
        impl.delete(entity, lockMode);
    }

    public void deleteByKey(K key) {
        impl.deleteByKey(key);
    }

    public void deleteAll() {
        impl.deleteAll();
    }

    public String toString() {
        return impl.toString();
    }

    public void merge(E entity)
            throws DataAccessException {
        impl.merge(entity);
    }

    public void evict(E entity)
            throws DataAccessException {
        impl.evict(entity);
    }

    public void replicate(E entity, ReplicationMode replicationMode)
            throws DataAccessException {
        impl.replicate(entity, replicationMode);
    }

    public void flush() {
        impl.flush();
    }

    public long count() {
        return impl.count();
    }

    public long count(Criterion... restrictions) {
        return impl.count(restrictions);
    }

}
