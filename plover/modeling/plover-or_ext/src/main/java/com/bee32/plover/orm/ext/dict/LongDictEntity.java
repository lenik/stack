package com.bee32.plover.orm.ext.dict;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@AttributeOverrides(@AttributeOverride(name = "name", column = @Column(length = 30)))
public abstract class LongDictEntity<K extends Serializable>
        extends DictEntity<K> {

    private static final long serialVersionUID = 1L;

    public LongDictEntity() {
        super();
    }

    public LongDictEntity(String name, String displayName) {
        super(name, displayName);
    }

    public LongDictEntity(String name, String displayName, String icon) {
        super(name, displayName, icon);
    }

}
