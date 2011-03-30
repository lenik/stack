package com.bee32.icsf.access;

import com.bee32.plover.orm.entity.EntityBean;

/**
 * <em><font color='red'>The modal logic of belief should be considered in more detail. </font></em>
 */
public abstract class Permission
        extends EntityBean<Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Unnamed-permission is not registered.
     */
    public Permission() {
        super("-");
    }

    /**
     * Named permission must be unique.
     *
     * @param name
     *            Non-<code>null</code> unique name of the permission.
     * @throws IllegalStateException
     *             If the name isn't unique.
     */
    public Permission(String name) {
        super(name);
        Permissions.register(this);
    }

    public abstract boolean implies(Permission permission);

    public Permission reduce() {
        return this;
    }

}
