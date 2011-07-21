package com.bee32.plover.criteria.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

public class ProjectionList
        extends ProjectionElement {

    private static final long serialVersionUID = 1L;

    List<ProjectionElement> elements;

    public ProjectionList() {
        elements = new ArrayList<ProjectionElement>();
    }

    public ProjectionList(ProjectionElement... elements) {
        for (ProjectionElement element : elements)
            this.elements.add(element);
    }

    @Override
    protected Projection buildProjection() {
        org.hibernate.criterion.ProjectionList projectionList = Projections.projectionList();
        for (ProjectionElement element : elements)
            projectionList.add(element.buildProjection());
        return projectionList;
    }

    public ProjectionList add(ProjectionElement proj) {
        elements.add(proj);
        return this;
    }

    // public ProjectionList add(Projection projection, String alias) {
    // return elements.add(projection, alias);
    // }

}
