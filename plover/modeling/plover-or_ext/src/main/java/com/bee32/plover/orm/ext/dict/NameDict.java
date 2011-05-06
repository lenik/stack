package com.bee32.plover.orm.ext.dict;

import javax.free.Nullables;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntitySpec;

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

    @Override
    protected Boolean equalsKey(EntitySpec<String> other) {
        return Nullables.equals(name, other.getName());
    }

    @Override
    protected boolean equalsEntity(EntitySpec<String> otherEntity) {
        return false;
    }

}