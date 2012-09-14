package com.bee32.plover.ox1.color;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.model.ModelTemplate;

@ModelTemplate
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

}
