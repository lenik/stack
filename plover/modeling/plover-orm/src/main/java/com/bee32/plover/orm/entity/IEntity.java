package com.bee32.plover.orm.entity;

import java.io.Serializable;

/**
 * @see javax.ejb.EntityBean
 */
public interface IEntity<K extends Serializable>
        extends Serializable {

    K getId();

}
