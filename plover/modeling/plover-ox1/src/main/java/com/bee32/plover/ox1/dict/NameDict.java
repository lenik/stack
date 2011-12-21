package com.bee32.plover.ox1.dict;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.DefaultValue;
import org.hibernate.annotations.Index;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.color.Blue;

/**
 * The id property of the {@link NameDict} equals to the <code>name</code> property.
 */
@MappedSuperclass
@Blue
public abstract class NameDict
        extends DictEntity<String> {

    private static final long serialVersionUID = 1L;

    public static final int ID_LENGTH = 20;

    protected int order;
    protected float rank;

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
    @Column(length = ID_LENGTH, unique = true)
    @Override
    public String getId() {
        return getName();
    }

    @Override
    protected void setId(String id) {
        setName(id);
    }

    @Column(nullable = false)
    @Index(name = "##_order")
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Column(nullable = false)
    @DefaultValue("0")
    // @Index(name = "##_rank")
    public float getRank() {
        return rank;
    }

    public void setRank(float rank) {
        this.rank = rank;
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
    protected ICriteriaElement selector(String prefix) {
        if (name == null)
            throw new NullPointerException("name");
        return new Equals(prefix + "name", name);
    }

}