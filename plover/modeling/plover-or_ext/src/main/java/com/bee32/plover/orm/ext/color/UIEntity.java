package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.Entity;

@MappedSuperclass
@Green
abstract class UIEntity<K extends Serializable>
        extends Entity<K>
        implements IUserFriendly {

    private static final long serialVersionUID = 1L;

    String label;
    String description;

    // IconRef

    public UIEntity() {
        super();
    }

    public UIEntity(String name) {
        super(name);
    }

    @Column(length = 30)
    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Column(length = 200)
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

}
