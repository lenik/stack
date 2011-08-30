package com.bee32.plover.criteria.hibernate;

public class GroupPropertyProjection
        extends PropertyProjection {

    private static final long serialVersionUID = 1L;

    public GroupPropertyProjection(String propertyName) {
        super(propertyName, true);
    }

    public static GroupPropertyProjection optional(String propertyName, Object property) {
        if (property == null)
            return null;
        else
            return new GroupPropertyProjection(propertyName);
    }

}
