package com.bee32.plover.orm.ext.dict;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * The id property of the {@link NameDict} equals to the <code>name</code> property.
 */
@MappedSuperclass
public abstract class NameDict
        extends DictEntity<String> {

    private static final long serialVersionUID = 1L;

    public NameDict() {
    }

    public NameDict(String name, String label) {
        this(name, label, "");
    }

    public NameDict(String name, String label, String description) {
        super(label, description);
        setName(name);
    }

    @Id
    @Column(length = 20, unique = true)
    @Override
    public String getId() {
        return getName();
    }

    @Override
    protected void setId(String id) {
        setName(id);
    }

    @Transient
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

}