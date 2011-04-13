package com.bee32.plover.orm.entity;

import java.io.Serializable;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.dao.GenericDao;

/**
 * &#64;Wireable is not inheritable, so you should add Configuration annotation in all concrete
 * Module classes, to enable Spring usage.
 *
 * @see GenericDao
 */
@ComponentTemplate
@Lazy
public abstract class EntityDao<E extends EntityBean<K>, K extends Serializable>
        extends HibernateEntityRepository<E, K> {

    public EntityDao() {
        super();
    }

    public EntityDao(String name) {
        super(name);
    }

}
