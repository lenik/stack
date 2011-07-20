package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;

public class Limit
        implements ICriteriaElement {

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

}
