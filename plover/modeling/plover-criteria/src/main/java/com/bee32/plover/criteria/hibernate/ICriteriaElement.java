package com.bee32.plover.criteria.hibernate;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.expression.EvaluationContext;

public interface ICriteriaElement
        extends Serializable {

    void apply(Criteria criteria);

    Criterion getCriterion();

    boolean filter(Object obj, EvaluationContext context);

    void format(StringBuilder out);

}
