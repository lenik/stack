package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

class SqlRestriction
        extends CriteriaElement {

    final String sql;
    final Object[] values;
    final Type[] types;

    public SqlRestriction(String sql, Object[] values, Type[] types) {
        this.sql = sql;
        this.values = values;
        this.types = types;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.sqlRestriction(sql, values, types);
    }

}
