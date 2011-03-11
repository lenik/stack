package com.bee32.icsf.access;

import com.bee32.icsf.access.authority.IAuthority;
import com.bee32.plover.orm.entity.Entity;

/**
 * <em><font color='red'>The modal logic of belief should be considered in more detail. </font></em>
 */
public abstract class Permission
        extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    private String displayName;
    private String description;

    public Permission() {
        super();
    }

    public Permission(String name) {
        super(name);
    }

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

    public abstract IAuthority getAuthority();

    public abstract boolean implies(Permission permission);

    public Permission reduce() {
        return this;
    }

}
