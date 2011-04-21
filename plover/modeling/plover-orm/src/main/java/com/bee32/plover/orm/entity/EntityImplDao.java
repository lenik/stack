package com.bee32.plover.orm.entity;

import java.io.Serializable;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.ComponentTemplate;

/**
 * @see EntityDao
 */
@ComponentTemplate
@Lazy
public class EntityImplDao<E extends IEntity<K>, K extends Serializable>
        extends EntityDao_H<E, K>
        implements IEntityDao<E, K> {

    public EntityImplDao() {
        super();
    }

    public EntityImplDao(String name) {
        super(name);
    }

}
