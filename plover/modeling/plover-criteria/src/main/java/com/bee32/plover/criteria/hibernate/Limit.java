package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;
import org.springframework.expression.EvaluationContext;

public class Limit
        implements ICriteriaElement {

    private static final long serialVersionUID = 1L;

    final int offset;
    final int limit;

    public Limit(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    @Override
    public void apply(Criteria criteria) {
        if (offset != -1) {
            criteria.setFirstResult(offset);
            criteria.setFetchSize(limit);
        }
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        // context.setlimit...?
        return true;
    }

}
