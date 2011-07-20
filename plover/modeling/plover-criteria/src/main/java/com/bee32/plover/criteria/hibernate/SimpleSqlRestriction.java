package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class SimpleSqlRestriction
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final String sql;

    public SimpleSqlRestriction(String sql) {
        this.sql = sql;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.sqlRestriction(sql);
    }

}
