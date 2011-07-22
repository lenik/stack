package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

public class MinProjection
        extends ProjectionElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;

    public MinProjection(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    protected Projection buildProjection() {
        return Projections.min(propertyName);
    }

}
