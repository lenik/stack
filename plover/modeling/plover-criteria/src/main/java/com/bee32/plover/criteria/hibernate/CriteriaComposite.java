package com.bee32.plover.criteria.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.EvaluationContext;

public class CriteriaComposite
        extends AbstractCriteriaElement {

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
        this.elements = new ArrayList<ICriteriaElement>();
        for (ICriteriaElement e : elements)
            if (e != null)
                this.elements.add(e);
    }

    @Override
    public void apply(Criteria criteria, int options) {
        for (ICriteriaElement element : elements)
            element.apply(criteria, options);
    }

    @Override
    public Criterion getCriterion(int options) {
        if (elements.isEmpty())
            return null;

        if (elements.size() == 1) {
            return elements.get(0).getCriterion(options);
        }

        Conjunction conj = Restrictions.conjunction();
        for (ICriteriaElement element : elements) {
            Criterion criterion = element.getCriterion(options);
            if (criterion != null)
                conj.add(criterion);
        }
        return conj;
    }

    public int size() {
        return elements.size();
    }

    public void add(ICriteriaElement element) {
        if (element == null)
            throw new NullPointerException("element");
        elements.add(element);
    }

    public void addAll(Collection<? extends ICriteriaElement> es) {
        for (ICriteriaElement e : es)
            if (e != null)
                elements.add(e);
    }

    public boolean remove(ICriteriaElement element) {
        return elements.remove(element);
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        for (ICriteriaElement element : elements)
            if (!element.filter(obj, context))
                return false;
        return true;
    }

    @Override
    public void format(StringBuilder out) {
        out.append("(comp");
        for (ICriteriaElement element : elements) {
            out.append(" ");
            element.format(out);
        }
        out.append(")");
    }

}
