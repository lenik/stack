package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

public class SumProjection
        extends ProjectionElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;

    public SumProjection(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    protected Projection buildProjection() {
        return Projections.sum(propertyName);
    }

    @Override
    public void format(StringBuilder out) {
        out.append("sum(");
        out.append(propertyName);
        out.append(")");
    }

}
