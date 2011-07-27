package com.bee32.plover.criteria.hibernate;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.springframework.expression.EvaluationContext;

public interface ICriteriaElement
        extends Serializable {

    void apply(Criteria criteria);

    boolean filter(Object obj, EvaluationContext context);

}
