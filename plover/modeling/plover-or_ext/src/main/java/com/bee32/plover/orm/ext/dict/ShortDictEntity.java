package com.bee32.plover.orm.ext.dict;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ShortDictEntity<K extends Serializable>
        extends DictEntity<K> {

    private static final long serialVersionUID = 1L;

    public ShortDictEntity() {
        super();
    }

    public ShortDictEntity(String name, String description, String icon) {
        super(name, description, icon);
    }

    @Column(length = 10, unique = true)
    // @Index("name")
    @Override
    public String getName() {
        return name;
    }

}
