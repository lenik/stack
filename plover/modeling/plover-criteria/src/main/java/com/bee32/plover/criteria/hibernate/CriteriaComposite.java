package com.bee32.plover.criteria.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;

public class CriteriaComposite
        implements ICriteriaElement {

    private static final long serialVersionUID = 1L;

    protected final List<ICriteriaElement> elements;

    public CriteriaComposite(ICriteriaElement[] av, ICriteriaElement... bv) {
        this(cat(av, bv));
    }

    static ICriteriaElement[] cat(ICriteriaElement[] car, ICriteriaElement[] cdr) {
        ICriteriaElement[] cat = new ICriteriaElement[car.length + cdr.length];
        System.arraycopy(car, 0, cat, 0, car.length);
        System.arraycopy(cdr, 0, cat, car.length, cdr.length);
        return cat;
    }

    public CriteriaComposite(ICriteriaElement... elements) {
        this(Arrays.asList(elements));
    }

    public CriteriaComposite(List<? extends ICriteriaElement> elements) {
        if (elements == null)
            throw new NullPointerException("elements");
        this.elements = new ArrayList<ICriteriaElement>(elements);
    }

    @Override
    public void apply(Criteria criteria) {
        for (ICriteriaElement element : elements) {
            if (element == null)
                continue;
            element.apply(criteria);
        }
    }

    public int size() {
        return elements.size();
    }

    public void add(ICriteriaElement element) {
        elements.add(element);
    }

    public void addAll(Collection<? extends ICriteriaElement> es) {
        elements.addAll(es);
    }

    public boolean remove(ICriteriaElement element) {
        return elements.remove(element);
    }

}
