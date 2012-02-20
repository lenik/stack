package com.bee32.plover.orm.entity;

import java.io.Serializable;

import com.bee32.plover.arch.naming.INamed;

/**
 * @see javax.ejb.EntityBean
 */
public interface IEntity<K extends Serializable>
        extends IIdentity<K>, INamed, Serializable {

    /**
     * Detach.
     *
     * @return This object itself.
     */
    Object detach();

}
