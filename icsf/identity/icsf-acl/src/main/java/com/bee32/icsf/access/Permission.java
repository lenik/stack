package com.bee32.icsf.access;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bee32.icsf.access.authority.IAuthority;
import com.bee32.plover.orm.entity.EntityBean;

/**
 * <em><font color='red'>The modal logic of belief should be considered in more detail. </font></em>
 */
@Entity
@Table(name = "_Permission")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "stereo", length = 10)
public abstract class Permission
        extends EntityBean<Integer> {

    private static final long serialVersionUID = 1L;

    private String displayName;
    private String description;

    public Permission() {
        super();
    }

    public Permission(String name) {
        super(name);
    }

    @Column(length = 60)
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Column(length = 60)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Transient
    public abstract IAuthority getAuthority();

    public abstract boolean implies(Permission permission);

    public Permission reduce() {
        return this;
    }

}
