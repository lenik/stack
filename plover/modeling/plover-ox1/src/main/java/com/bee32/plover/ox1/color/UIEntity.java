package com.bee32.plover.ox1.color;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.ox1.c.CEntity;

@MappedSuperclass
@Green
abstract class UIEntity<K extends Serializable>
        extends CEntity<K>
        implements IUserFriendly {

    private static final long serialVersionUID = 1L;

    public static final int LABEL_LENGTH = 40;
    public static final int DESCRIPTION_LENGTH = 200;

    protected String label;
    protected String description;

    // IconRef

    public UIEntity() {
        super();
    }

    public UIEntity(String name) {
        super(name);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof UIEntity<?>) {
            UIEntity<?> o = (UIEntity<?>) source;
            _populate(o);
        } else
            super.populate(source);
    }

    protected void _populate(UIEntity<?> o) {
        super._populate(o);
        // name=o.name;
        label = o.label;
        description = o.description;
    }

    @Column(length = LABEL_LENGTH)
    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        if (label != null) {
            label = label.trim();
            if (label.isEmpty())
                label = null;
        }
        this.label = label;
    }

    @Column(length = DESCRIPTION_LENGTH)
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        if (description != null) {
            description = description.trim();
            if (description.isEmpty())
                description = null;
        }
        this.description = description;
    }

}
