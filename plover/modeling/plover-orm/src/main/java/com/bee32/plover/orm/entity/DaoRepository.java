package com.bee32.plover.orm.entity;

import java.io.Serializable;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.ComponentTemplate;

/**
 * @see EntityDao
 */
@ComponentTemplate
@Lazy
public class DaoRepository<E extends IEntity<K>, K extends Serializable>
        extends HibernateEntityRepository<E, K>
        implements IDaoRepository<E, K> {

    public DaoRepository() {
        super();
    }

    public DaoRepository(String name) {
        super(name);
    }

}
