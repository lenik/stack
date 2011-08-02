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

    @Override
    public void apply(Criteria criteria) {
        for (ICriteriaElement e : elements)
            e.apply(criteria);
    }

    @Override
    protected Criterion buildCriterion() {
        org.hibernate.criterion.Disjunction disj = Restrictions.disjunction();
        for (ICriteriaElement e : elements) {
            Criterion criterion = e.getCriterion();
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

}
