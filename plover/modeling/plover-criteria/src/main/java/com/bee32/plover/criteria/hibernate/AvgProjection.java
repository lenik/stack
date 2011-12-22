package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

public class AvgProjection
        extends ProjectionElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;

    public AvgProjection(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    protected Projection buildProjection() {
        return Projections.avg(propertyName);
    }

    @Override
    public void format(StringBuilder out) {
        out.append("(avg ");
        out.append(propertyName);
        out.append(")");
    }

}
