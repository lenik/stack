package com.bee32.plover.orm.web;

import java.io.Serializable;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;

public interface IEntityForming<E extends Entity<K>, K extends Serializable> {

    void createForm(E entity, EntityDto<E, K> dto);

    /**
     * Load the form in DTO from the entity.
     *
     * @param entity
     *            The source entity.
     */
    void loadForm(E entity, EntityDto<E, K> dto);

    /**
     * Save the form
     */
    void saveForm(E entity, EntityDto<E, K> dto);

}
