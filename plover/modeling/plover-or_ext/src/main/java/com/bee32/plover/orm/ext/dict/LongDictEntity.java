package com.bee32.plover.orm.ext.dict;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class LongDictEntity<K extends Serializable>
        extends DictEntity<K> {

    private static final long serialVersionUID = 1L;

    public LongDictEntity() {
        super();
    }

    public LongDictEntity(String name, String description, String icon) {
        super(name, description, icon);
    }

    @Column(length = 30, unique = true)
    // @Index("name")
    @Override
    public String getName() {
        return name;
    }

}
