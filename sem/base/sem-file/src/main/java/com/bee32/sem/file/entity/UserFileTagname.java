package com.bee32.sem.file.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 用户用语文件分类的标签。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "user_file_tagname_seq", allocationSize = 1)
public class UserFileTagname
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 30;

    public UserFileTagname() {
    }

    public UserFileTagname(String name) {
        super(name);
    }

    /**
     * 标签名字
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
