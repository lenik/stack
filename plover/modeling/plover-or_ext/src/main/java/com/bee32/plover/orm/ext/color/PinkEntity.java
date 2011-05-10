package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public abstract class PinkEntity<K extends Serializable>
        extends Entity<K> {

    private static final long serialVersionUID = 1L;

    protected K id;

    public PinkEntity() {
        super();
    }

    public PinkEntity(String name) {
        super(name);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public K getId() {
        return id;
    }

    protected void setId(K id) {
        this.id = id;
    }

    @Override
    protected final Boolean equalsKey(Entity<K> other) {
        return equalsKey((PinkEntity<K>) other);
    }

    protected Boolean equalsKey(PinkEntity<K> other) {
        return super.equalsKey(other);
    }

    @Override
    protected final boolean equalsEntity(Entity<K> otherEntity) {
        return equalsEntity((PinkEntity<K>) otherEntity);
    }

    protected boolean equalsEntity(PinkEntity<K> otherEntity) {
        return super.equalsEntity(otherEntity);
    }

}
