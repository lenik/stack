package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.Entity;

/**
 * <b>pink</b>
 * <p>
 * <i>moment-interval</i> — Does it represent a moment or interval of time? An example would be an
 * object that temporarily stores login information during the authentication process.
 *
 * @see http://en.wikipedia.org/wiki/UML_colors
 */
@MappedSuperclass
@AttributeOverrides({//
/*    */@AttributeOverride(name = "label", column = @Column(length = 50)) })
public abstract class GreenEntity<K extends Serializable>
        extends UserFriendlyEntity<K> {

    private static final long serialVersionUID = 1L;

    public GreenEntity() {
        super();
    }

    public GreenEntity(String name) {
        super(name);
    }

    @Override
    protected final Boolean equalsKey(Entity<K> other) {
        return equalsKey((GreenEntity<K>) other);
    }

    protected Boolean equalsKey(GreenEntity<K> other) {
        return super.equalsKey(other);
    }

    @Override
    protected final boolean equalsEntity(Entity<K> otherEntity) {
        return equalsEntity((GreenEntity<K>) otherEntity);
    }

    protected boolean equalsEntity(GreenEntity<K> otherEntity) {
        return super.equalsEntity(otherEntity);
    }

}
