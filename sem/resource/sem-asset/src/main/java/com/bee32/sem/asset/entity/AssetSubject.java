package com.bee32.sem.asset.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.tree.TreeEntityAuto;

/**
 * 科目
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "asset_subject_seq", allocationSize = 1)
public class AssetSubject
        extends TreeEntityAuto<Integer, AssetSubject> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 4;

    public AssetSubject() {
        super();
    }

    public AssetSubject(String name) {
        super(name);
    }

    public AssetSubject(AssetSubject parent) {
        super(parent, null);
    }

    public AssetSubject(AssetSubject parent, String name) {
        super(parent, name);
    }

    @NaturalId(mutable = true)
    @Column(length = NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
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
