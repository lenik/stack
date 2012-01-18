package com.bee32.plover.criteria.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.EvaluationContext;

public class Conjunction
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    List<ICriteriaElement> elements = new ArrayList<ICriteriaElement>();

    public Conjunction() {
    }

    public Conjunction(ICriteriaElement... elements) {
        for (ICriteriaElement e : elements)
            if (e != null)
                this.elements.add(e);
    }

    @Override
    public void apply(Criteria criteria, int options) {
        for (ICriteriaElement e : elements)
            e.apply(criteria, options);
    }

    @Override
    protected Criterion buildCriterion(int options) {
        org.hibernate.criterion.Conjunction conj = Restrictions.conjunction();
        for (ICriteriaElement e : elements) {
            Criterion criterion = e.getCriterion(options);
            if (criterion != null)
                conj.add(criterion);
        }
        return conj;
    }

    public Conjunction add(CriteriaElement element) {
        if (element != null)
            elements.add(element);
        return this;
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
        out.append("(conj");
        for (ICriteriaElement element : elements) {
            out.append(" ");
            element.format(out);
        }
        out.append(")");
    }

}
