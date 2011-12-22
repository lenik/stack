package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

public class Distinct
        extends ProjectionElement {

    private static final long serialVersionUID = 1L;

    ProjectionElement element;

    public Distinct(ProjectionElement element) {
        if (element == null)
            throw new NullPointerException("element");
        this.element = element;
    }

    @Override
    protected Projection buildProjection() {
        Projection projection = element.buildProjection();
        return Projections.distinct(projection);
    }

    @Override
    public void format(StringBuilder out) {
        out.append("(distinct ");
        element.format(out);
        out.append(")");
    }

}
