package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.EntityAuto;

/**
 * <b>pink</b>
 * <p>
 * <i>moment-interval</i> — Does it represent a moment or interval of time? An example would be an
 * object that temporarily stores login information during the authentication process.
 *
 * @see http://en.wikipedia.org/wiki/UML_colors
 */
@MappedSuperclass
public abstract class PinkEntity<K extends Serializable>
        extends EntityAuto<K> {

    private static final long serialVersionUID = 1L;

    public PinkEntity() {
        super();
    }

    public PinkEntity(String name) {
        super(name);
    }

}
