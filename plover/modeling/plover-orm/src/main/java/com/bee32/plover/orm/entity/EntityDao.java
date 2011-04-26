package com.bee32.plover.orm.entity;

import java.io.Serializable;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.ComponentTemplate;

/**
 * &#64;Wireable is not inheritable, so you should add Configuration annotation in all concrete
 * Module classes, to enable Spring usage.
 */
@ComponentTemplate
@Lazy
public abstract class EntityDao<E extends Entity<K>, K extends Serializable>
        extends EntityDao_H<E, K>
        implements IEntityDao<E, K> {

    public EntityDao() {
        super();
    }

    public EntityDao(String name) {
        super(name);
    }

    @Override
    public E get(K key) {
        E entity = super.get(key);
        if (entity == null)
            return null;
        else
            entity = _preinit(entity);
        return entity;
    }

    @Override
    public E load(K key) {
        E entity = super.load(key);
        entity = _preinit(entity);
        return entity;
    }

    protected E _preinit(E entity) {
        return entity;
    }

}
