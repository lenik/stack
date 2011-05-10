package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.EntityBase;

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
public abstract class GreenEntitySpec<K extends Serializable>
        extends UserFriendlyEntity<K> {

    private static final long serialVersionUID = 1L;

    public GreenEntitySpec() {
        super();
    }

    public GreenEntitySpec(String name) {
        super(name);
    }

    @Override
    protected Boolean naturalEquals(EntityBase<K> other) {
        String name = getName();
        String otherName = other.getName();
        if (name == null || otherName == null)
            return false;

        if (!name.equals(otherName))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        String name = getName();

        if (name == null)
            return 0;
        else
            return name.hashCode();
    }

}
