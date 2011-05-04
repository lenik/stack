package com.bee32.plover.orm.ext.dict;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class NameDict
        extends DictEntity<String> {

    private static final long serialVersionUID = 1L;

    public NameDict() {
    }

    public NameDict(String name, String alias) {
        this(name, alias, "");
    }

    public NameDict(String name, String alias, String description) {
        super(alias, description);
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    @Transient
    @Override
    public String getId() {
        return getName();
    }

    @Override
    protected void setId(String id) {
        setName(id);
    }

    @Id
    @Column(length = 20, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}