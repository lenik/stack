package com.bee32.plover.orm.entity;

import java.io.Serializable;

import javax.persistence.EntityManager;

import com.bee32.plover.orm.dao.JpaTemplate;

public abstract class JpaEntityRepository<E extends Entity<K>, K extends Serializable>
        extends EntityRepository<E, K> {

    private JpaTemplate template;

    public JpaEntityRepository() {
        super();
    }

    public JpaEntityRepository(String name) {
        super(name);
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
    public boolean containsKey(Object _key) {
        if (!(keyType.isInstance(_key)))
            return false;
        K key = keyType.cast(_key);
        return get(key) != null;
    }

    @Override
    public E get(K key) {
        E entity = template.find(entityType, key);
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
    public boolean deleteByKey(K key) {
        E entity = get(key);
        if (entity != null) {
            template.remove(entity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Object entity) {
        template.remove(entity);
        return true;
    }

}
