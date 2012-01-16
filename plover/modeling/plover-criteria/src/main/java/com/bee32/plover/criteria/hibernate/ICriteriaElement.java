package com.bee32.plover.criteria.hibernate;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.springframework.expression.EvaluationContext;

public interface ICriteriaElement
        extends Serializable {

    int NO_ORDER = 1;
    int NO_PROJECTION = 2;
    int NO_PAGINATION = 4;
    int NO_MODIFIERS = NO_ORDER | NO_PROJECTION | NO_PAGINATION;
    int OPTIM_COUNT = 0x100;

    void apply(Criteria criteria);

    void apply(Criteria criteria, int options);

    /**
     * Get the real {@link Criterion} object for hibernate.
     *
     * @return <code>null</code> if no criterion, or {@link Conjunction} if more then single.
     */
    Criterion getCriterion();

    /**
     * Get the real {@link Criterion} object for hibernate.
     *
     * @return <code>null</code> if no criterion, or {@link Conjunction} if more then single.
     */
    Criterion getCriterion(int options);

    boolean filter(Object obj, EvaluationContext context);

    void format(StringBuilder out);

}
