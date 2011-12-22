package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

public class RowCountProjection
        extends ProjectionElement {

    private static final long serialVersionUID = 1L;

    @Override
    protected Projection buildProjection() {
        return Projections.rowCount();
    }

    @Override
    public void format(StringBuilder out) {
        out.append("(row-count)");
    }

}
