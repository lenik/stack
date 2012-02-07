package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.expression.EvaluationContext;

public class SqlRestriction
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final String sql;
    final Object[] values;
    final Type[] types;

    public SqlRestriction(String sql) {
        this(sql, new Object[0], new Type[0]);
    }

    public SqlRestriction(String sql, Object value, Type type) {
        this(sql, new Object[] { value }, new Type[] { type });
    }

    public SqlRestriction(String sql, Object[] values, Type[] types) {
        this.sql = sql;
        this.values = values;
        this.types = types;
    }

    @Override
    public void apply(Criteria criteria, int options) {
    }

    @Override
    protected Criterion buildCriterion(int options) {
        return Restrictions.sqlRestriction(sql, values, types);
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        throw new UnsupportedOperationException("filter by sql criterion isn't supported");
    }

    @Override
    public void format(StringBuilder out) {
        String quoted = "\"" + sql + "\"";
        out.append("(sql-restriction ");
        out.append(quoted);
        for (Object value : values) {
            out.append(" ");
            out.append(value);
        }
        out.append(")");
    }

}
