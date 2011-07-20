package com.bee32.plover.criteria.hibernate;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;

public class CriteriaComposite
        implements ICriteriaElement {

    final List<? extends ICriteriaElement> elements;

    public CriteriaComposite(List<? extends ICriteriaElement> elements) {
        if (elements == null)
            throw new NullPointerException("elements");
        this.elements = elements;
    }

    public CriteriaComposite(ICriteriaElement... elements) {
        if (elements == null)
            throw new NullPointerException("elements");
        this.elements = Arrays.asList(elements);
    }

    @Override
    public void apply(Criteria criteria) {
        for (ICriteriaElement element : elements) {
            if (element == null)
                continue;
            element.apply(criteria);
        }
    }

}
