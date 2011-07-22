package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

public class PropertyProjection
        extends ProjectionElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;
    final boolean group;

    public PropertyProjection(String propertyName) {
        this(propertyName, false);
    }

    public PropertyProjection(String propertyName, boolean group) {
        this.propertyName = propertyName;
        this.group = group;
    }

    @Override
    protected Projection buildProjection() {
        if (group)
            return Projections.groupProperty(propertyName);
        else
            return Projections.property(propertyName);
    }

}
