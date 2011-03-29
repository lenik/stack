package com.bee32.icsf.access;

import com.bee32.plover.orm.entity.EntityBean;

/**
 * <em><font color='red'>The modal logic of belief should be considered in more detail. </font></em>
 */
public abstract class Permission
        extends EntityBean<Integer> {

    private static final long serialVersionUID = 1L;

    public Permission() {
        super();
    }

    public Permission(String name) {
        super(name);
    }

    public abstract boolean implies(Permission permission);

    public Permission reduce() {
        return this;
    }

}
