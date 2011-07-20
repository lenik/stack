package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class SimpleSqlRestriction
        extends CriteriaElement {

    final String sql;

    public SimpleSqlRestriction(String sql) {
        this.sql = sql;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.sqlRestriction(sql);
    }

}
