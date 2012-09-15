package com.bee32.sem.file.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.Identity;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 用户用于文件分类的标签。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "user_file_tagname_seq", allocationSize = 1)
public class UserFileTagname
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 30;

    int refCount;

    public UserFileTagname() {
    }

    public UserFileTagname(String name) {
        super(name);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof UserFileTagname)
            _populate((UserFileTagname) source);
        else
            super.populate(source);
    }

    protected void _populate(UserFileTagname o) {
        super._populate(o);
        this.refCount = o.refCount;
    }

    /**
     * 标签名
     *
     * 文件标签的显示名称。
     */
    @NaturalId
    @Column(length = NAME_LENGTH, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("tag");
        this.name = name;
    }

    /**
     * 引用计数
     *
     * 文件标签被使用的次数。
     */
    @Formula("(select count(*) from user_file_tags x where x.tag=id)")
    public int getRefCount() {
        return refCount;
    }

    public void setRefCount(int refCount) {
        this.refCount = refCount;
    }

    @Override
    protected Serializable naturalId() {
        if (name == null)
            return new Identity(this);
        return name;
    }

    @Override
    protected CriteriaElement selector(String prefix) {
        if (name == null)
            throw new NullPointerException("name");
        return new Equals(prefix + "name", name);
    }

}
