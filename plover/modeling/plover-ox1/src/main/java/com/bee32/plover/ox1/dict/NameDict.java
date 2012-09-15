package com.bee32.plover.ox1.dict;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.DefaultValue;
import org.hibernate.annotations.Index;

import com.bee32.plover.arch.util.Identity;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.model.ModelTemplate;
import com.bee32.plover.ox1.color.Blue;

/**
 * The id property of the {@link NameDict} equals to the <code>name</code> property.
 */
@ModelTemplate
@MappedSuperclass
@Blue
public abstract class NameDict
        extends DictEntity<String> {

    private static final long serialVersionUID = 1L;

    public static final int ID_LENGTH = 20;

    String id;
    int order;
    float rank;

    public NameDict() {
    }

    public NameDict(String id, String label) {
        this(0, id, label, null);
    }

    public NameDict(String id, String label, String description) {
        this(0, id, label, description);
    }

    public NameDict(int order, String id, String label) {
        this(order, id, label, null);
    }

    public NameDict(int order, String id, String label, String description) {
        super(label, description);
        this.id = id;
        this.order = order;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof NameDict)
            _populate((NameDict) source);
        else
            super.populate(source);
    }

    protected void _populate(NameDict o) {
        super._populate(o);
        order = o.order;
        rank = o.rank;
    }

    /**
     * 条目标识符
     *
     * 词典条目的标识符、主键。
     */
    @Id
    @Column(length = ID_LENGTH, unique = true)
    @Override
    public String getId() {
        return id;
    }

    @Override
    protected void setId(String id) {
        if (id == null)
            throw new NullPointerException("id");
        this.id = id;
    }

    /**
     * 次序
     *
     * 用于对条目排序。
     */
    @Column(nullable = false)
    @Index(name = "##_order")
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * 权重
     *
     * 条目相关的权重（可选）。
     */
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
        return getId();
    }

    public void setName(String name) {
        setId(name);
    }

    @Override
    protected Serializable naturalId() {
        if (id == null)
            return new Identity(this);
        else
            return id;
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (id == null)
            throw new NullPointerException("id");
        else
            return new Equals(prefix + "id", id);
    }

}
