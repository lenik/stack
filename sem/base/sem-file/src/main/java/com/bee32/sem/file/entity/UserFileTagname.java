package com.bee32.sem.file.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.ext.color.UIEntityAuto;

/**
 * 用户用语文件分类的标签。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "user_file_tagname_seq", allocationSize = 1)
public class UserFileTagname
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    String tag;

    /**
     * 标签名字
     */
    @NaturalId
    @Column(length = 30, nullable = false)
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        if (tag == null)
            throw new NullPointerException("tag");
        this.tag = tag;
    }

    @Override
    protected Serializable naturalId() {
        if (tag == null)
            return new DummyId(this);
        return tag;
    }

    @Override
    protected CriteriaElement selector(String prefix) {
        if (tag == null)
            throw new NullPointerException("tag");
        return new Equals(prefix + "tag", tag);
    }

}
