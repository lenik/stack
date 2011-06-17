package com.bee32.plover.orm.ext.dict;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.Blue;

/**
 * The id property of the {@link NameDict} equals to the <code>name</code> property.
 */
@MappedSuperclass
@Blue
public abstract class NameDict
        extends DictEntity<String> {

    private static final long serialVersionUID = 1L;

    protected int order;

    public NameDict() {
    }

    public NameDict(String name, String label) {
        super(label);
        this.name = name;
    }

    public NameDict(String name, String label, String description) {
        super(label, description);
        this.name = name;
    }

    public NameDict(int order, String name, String label) {
        super(label);
        this.order = order;
        this.name = name;
    }

    public NameDict(int order, String name, String label, String description) {
        super(label, description);
        this.order = order;
        this.name = name;
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

    // TODO @Index
    @Column(nullable = false)
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    @Override
    protected Boolean naturalEquals(EntityBase<String> other) {
        String name = getName();
        String otherName = other.getName();
        if (name == null || otherName == null)
            return false;

        if (!name.equals(otherName))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        String name = getName();

        if (name == null)
            return 0;
        else
            return name.hashCode();
    }

}