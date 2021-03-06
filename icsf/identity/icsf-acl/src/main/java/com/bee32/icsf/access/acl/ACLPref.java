package com.bee32.icsf.access.acl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.ox1.typePref.TypePrefEntity;

/**
 * 默认的记录安全策略
 *
 * 默认的记录安全策略配置项。
 * <p lang="en">
 * Entity/ACL Preference
 */
@Entity
public class ACLPref
        extends TypePrefEntity {

    private static final long serialVersionUID = 1L;

    public static final int DESCRIPTION_LENGTH = 200;

    private ACL preferredACL;
    private String description;

    @Override
    public void populate(Object source) {
        if (source instanceof ACLPref)
            _populate((ACLPref) source);
        else
            super.populate(source);
    }

    protected void _populate(ACLPref o) {
        super._populate(o);
        this.preferredACL = o.preferredACL;
        this.description = o.description;
    }

    @ManyToOne
    public ACL getPreferredACL() {
        return preferredACL;
    }

    public void setPreferredACL(ACL preferredACL) {
        this.preferredACL = preferredACL;
    }

    @Column(length = DESCRIPTION_LENGTH)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
