package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.EntityBase;

@MappedSuperclass
@AttributeOverrides({//
/*    */@AttributeOverride(name = "label", column = @Column(length = 50)) })
public abstract class UIEntitySpec<K extends Serializable>
        extends UIEntity<K> {

    private static final long serialVersionUID = 1L;

    public UIEntitySpec() {
        super();
    }

    public UIEntitySpec(String name) {
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
