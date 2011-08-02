package com.bee32.plover.orm.ext.dict;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.Equals;
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
    protected Serializable naturalId() {
        if (name == null)
            return new DummyId(this);
        return name;
    }

    @Override
    protected CriteriaElement selector(String prefix) {
        if (name == null)
            throw new NullPointerException("name");
        return new Equals(prefix + "name", name);
    }

}