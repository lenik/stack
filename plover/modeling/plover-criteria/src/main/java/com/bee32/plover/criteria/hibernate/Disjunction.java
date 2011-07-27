package com.bee32.plover.criteria.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.EvaluationContext;

public class Disjunction
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    List<CriteriaElement> elements = new ArrayList<CriteriaElement>();

    Disjunction() {
    }

    @Override
    protected Criterion buildCriterion() {
        org.hibernate.criterion.Disjunction disj = Restrictions.disjunction();
        for (CriteriaElement e : elements) {
            Criterion criterion = e.buildCriterion();
            disj.add(criterion);
        }
        return disj;
    }

    public Disjunction add(CriteriaElement element) {
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
