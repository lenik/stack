package com.bee32.plover.orm.entity;

import java.io.Serializable;

import javax.persistence.EntityManager;

import com.bee32.plover.orm.dao.JpaTemplate;

public abstract class JpaEntityRepository<E extends IEntity<K>, K extends Serializable>
        extends EntityRepository<E, K> {

    private JpaTemplate template;

    public JpaEntityRepository(Class<E> entityType, Class<K> keyType) {
        super(entityType, keyType);
    }

    public EntityManager getEntityManager() {
        if (template == null)
            return null;
        else
            return template.getEntityManager();
    }

    public void setEntityManager(EntityManager entityManager) {
        if (template == null || template.getEntityManager() != entityManager)
            template = new JpaTemplate(entityManager);
    }

    @Override
    public boolean contains(Object entity) {
        return template.contains(entity);
    }

    @Override
    public boolean containsKey(Serializable key) {
        return retrieve(key) != null;
    }

    @Override
    public E retrieve(Serializable key) {
        // TODO SHOULD convert key -> serializable.
        E entity = template.find(instanceType, key);
        return entity;
    }

    @Override
    public K save(E entity) {
        template.persist(entity);
        return getKey(entity);
    }

    @Override
    public void update(E entity) {
        template.persist(entity);
    }

    @Override
    public void refresh(E entity) {
        template.refresh(entity);
    }

    @Override
    public void deleteByKey(Serializable key) {
        E entity = retrieve(key);
        if (entity != null)
            template.remove(entity);
    }

    @Override
    public void delete(Object entity) {
        template.remove(entity);
    }

}
