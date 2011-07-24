package com.bee32.sem.file.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.UIEntityAuto;

/**
 * 用户用语文件分类的标签。
 */
@Entity
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
    protected Boolean naturalEquals(EntityBase<Long> other) {
        UserFileTagname o = (UserFileTagname) other;
        if (!tag.equals(o.tag))
            return false;
        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;
        hash += tag.hashCode();
        return hash;
    }

}
