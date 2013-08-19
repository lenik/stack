package com.bee32.sem.people.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.bee32.plover.ox1.dict.NameDict;

/**
 * 涉众分类标签
 *
 * 用于对自然人或组织机构进行分类。
 *
 * <p lang="en">
 * Party Tag
 */
@Entity
public class PartyTagname
        extends NameDict {

    private static final long serialVersionUID = 1L;

    boolean internal;
    Set<Party> instances = new HashSet<Party>();

    public PartyTagname() {
        super();
    }

    public PartyTagname(String name, String label) {
        super(name, label);
    }

    public PartyTagname(String name, String label, boolean internal) {
        super(name, label);
        this.internal = internal;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof PartyTagname)
            _populate((PartyTagname) source);
        else
            super.populate(source);
    }

    protected void _populate(PartyTagname o) {
        super._populate(o);
        internal = o.internal;
        instances = new HashSet<Party>(o.instances);
    }

    /**
     * 内部标签
     *
     * 表明仅在公司内部使用的标签。
     */
    public boolean isInternal() {
        return internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    /**
     * 实例集
     *
     * 设置了本标签的涉众实例集合。
     *
     * @see Party#getTags()
     */
    @ManyToMany(mappedBy = "tags")
    public Set<Party> getInstances() {
        instances = new HashSet<Party>();
        return instances;
    }

    public void setInstances(Set<Party> instances) {
        if (instances == null)
            throw new NullPointerException("instances");
        this.instances = instances;
    }

}
