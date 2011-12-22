package com.bee32.plover.criteria.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

public class ProjectionList
        extends ProjectionElement {

    private static final long serialVersionUID = 1L;

    List<ProjectionElement> elements = new ArrayList<ProjectionElement>();

    public ProjectionList() {
    }

    public ProjectionList(ProjectionElement... elements) {
        for (ProjectionElement element : elements) {
            if (element == null)
                continue;
            this.elements.add(element);
        }
    }

    @Override
    protected Projection buildProjection() {
        org.hibernate.criterion.ProjectionList projectionList = Projections.projectionList();
        for (ProjectionElement element : elements)
            projectionList.add(element.buildProjection());
        return projectionList;
    }

    public ProjectionList add(ProjectionElement projectionElement) {
        if (projectionElement == null)
            throw new NullPointerException("projectionElement");
        elements.add(projectionElement);
        return this;
    }

    // public ProjectionList add(Projection projection, String alias) {
    // return elements.add(projection, alias);
    // }

    @Override
    public void format(StringBuilder out) {
        out.append("(projection-list");
        for (ProjectionElement element : elements) {
            out.append(" ");
            element.format(out);
        }
        out.append(")");
    }

}
