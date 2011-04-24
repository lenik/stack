package com.bee32.plover.orm.ext.dict;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@AttributeOverrides(@AttributeOverride(name = "name", column = @Column(length = 10)))
public abstract class ShortDictEntity<K extends Serializable>
        extends DictEntity<K> {

    private static final long serialVersionUID = 1L;

    public ShortDictEntity() {
        super();
    }

    public ShortDictEntity(String name, String displayName) {
        super(name, displayName);
    }

    public ShortDictEntity(String name, String displayName, String icon) {
        super(name, displayName, icon);
    }

}
