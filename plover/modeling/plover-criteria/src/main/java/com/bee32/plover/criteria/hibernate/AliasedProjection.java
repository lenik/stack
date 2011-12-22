package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

public class AliasedProjection
        extends ProjectionElement {

    private static final long serialVersionUID = 1L;

    final ProjectionElement projectionElement;
    final String alias;

    public AliasedProjection(ProjectionElement projection, String alias) {
        this.projectionElement = projection;
        this.alias = alias;
    }

    @Override
    protected Projection buildProjection() {
        Projection projection = projectionElement.buildProjection();
        return Projections.alias(projection, alias);
    }

    @Override
    public void format(StringBuilder out) {
        out.append("(aliased ");
        out.append(alias);
        out.append(" ");
        projectionElement.format(out);
        out.append(")");
    }

}
