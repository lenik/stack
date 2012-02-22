package com.bee32.plover.criteria.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.EvaluationContext;

public class Disjunction
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    List<ICriteriaElement> elements = new ArrayList<ICriteriaElement>();

    public Disjunction() {
    }

    public Disjunction(ICriteriaElement... elements) {
        for (ICriteriaElement e : elements)
            if (e != null)
                this.elements.add(e);
    }

    public static ICriteriaElement of(ICriteriaElement... elements) {
        if (elements == null || elements.length == 0)
            return null;
        Disjunction disj = new Disjunction(elements);
        if (disj.elements.isEmpty())
            return null;
        if (disj.elements.size() == 1)
            return disj.elements.get(0);
        return disj;
    }

    @Override
    public void apply(Criteria criteria, int options) {
        for (ICriteriaElement e : elements)
            e.apply(criteria, options);
    }

    @Override
    protected Criterion buildCriterion(int options) {
        org.hibernate.criterion.Disjunction disj = Restrictions.disjunction();
        for (ICriteriaElement e : elements) {
            Criterion criterion = e.getCriterion(options);
            if (criterion != null)
                disj.add(criterion);
        }
        return disj;
    }

    public Disjunction add(ICriteriaElement element) {
        if (element != null)
            elements.add(element);
        return this;
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        for (ICriteriaElement element : elements)
            if (element.filter(obj, context))
                return true;
        return false;
    }

    @Override
    public void format(StringBuilder out) {
        out.append("(disj");
        for (ICriteriaElement element : elements) {
            out.append(" ");
            element.format(out);
        }
        out.append(")");
    }

}
