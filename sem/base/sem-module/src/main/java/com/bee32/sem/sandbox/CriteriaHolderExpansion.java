package com.bee32.sem.sandbox;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.expression.EvaluationContext;

import com.bee32.plover.criteria.hibernate.AbstractCriteriaElement;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class CriteriaHolderExpansion
        extends AbstractCriteriaElement {

    private static final long serialVersionUID = 1L;

    final ICriteriaHolder holder;

    public CriteriaHolderExpansion(ICriteriaHolder holder) {
        if (holder == null)
            throw new NullPointerException("holder");
        this.holder = holder;
    }

    @Override
    public void apply(Criteria criteria, int options) {
        ICriteriaElement element = holder.getCriteriaElement();
        if (element != null)
            element.apply(criteria, options);
    }

    @Override
    public Criterion getCriterion(int options) {
        ICriteriaElement element = holder.getCriteriaElement();
        if (element != null)
            return element.getCriterion(options);
        else
            return null;
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        ICriteriaElement element = holder.getCriteriaElement();
        if (element == null)
            return true;
        else
            return element.filter(obj, context);
    }

    @Override
    public void format(StringBuilder out) {
        ICriteriaElement element = holder.getCriteriaElement();
        if (element == null) {
            out.append("(<none>)");
        } else {
            element.format(out);
        }
    }

    @Override
    public String toString() {
        ICriteriaElement element = holder.getCriteriaElement();
        if (element == null)
            return "(<none>)";
        else
            return element.toString();
    }

}
