package com.bee32.plover.criteria.hibernate;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.springframework.expression.EvaluationContext;

public interface ICriteriaElement
        extends Serializable {

    void apply(Criteria criteria);

    /**
     * Get the real {@link Criterion} object for hibernate.
     *
     * @return <code>null</code> if no criterion, or {@link Conjunction} if more then single.
     */
    Criterion getCriterion();

    boolean filter(Object obj, EvaluationContext context);

    void format(StringBuilder out);

}
