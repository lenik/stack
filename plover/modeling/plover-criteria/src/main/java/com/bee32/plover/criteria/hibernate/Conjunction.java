package com.bee32.plover.criteria.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.EvaluationContext;

public class Conjunction
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    List<CriteriaElement> elements = new ArrayList<CriteriaElement>();

    public Conjunction() {
    }

    public Conjunction(CriteriaElement... elements) {
        for (CriteriaElement e : elements)
            this.elements.add(e);
    }

    @Override
    protected Criterion buildCriterion() {
        org.hibernate.criterion.Conjunction conj = Restrictions.conjunction();
        for (CriteriaElement e : elements) {
            Criterion criterion = e.buildCriterion();
            conj.add(criterion);
        }
        return conj;
    }

    public Conjunction add(CriteriaElement element) {
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

}
