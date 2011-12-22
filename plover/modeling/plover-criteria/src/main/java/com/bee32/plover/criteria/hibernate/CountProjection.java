package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

public class CountProjection
        extends ProjectionElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;
    final boolean distinct;

    public CountProjection(String propertyName) {
        this(propertyName, false);
    }

    public CountProjection(String propertyName, boolean distinct) {
        this.propertyName = propertyName;
        this.distinct = distinct;
    }

    @Override
    protected Projection buildProjection() {
        if (distinct)
            return Projections.countDistinct(propertyName);
        else
            return Projections.count(propertyName);
    }

    @Override
    public void format(StringBuilder out) {
        if (distinct)
            out.append("(count-distinct ");
        else
            out.append("(count ");
        out.append(propertyName);
        out.append(")");
    }

}
