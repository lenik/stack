package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

public class IdentifierProjection
        extends ProjectionElement {

    private static final long serialVersionUID = 1L;

    @Override
    protected Projection buildProjection() {
        return Projections.id();
    }

    @Override
    public void format(StringBuilder out) {
        out.append("(id)");
    }

}
