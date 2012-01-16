package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.EvaluationContext;

public class SimpleSqlRestriction
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final String sql;

    public SimpleSqlRestriction(String sql) {
        this.sql = sql;
    }

    @Override
    public void apply(Criteria criteria, int options) {
    }

    @Override
    protected Criterion buildCriterion(int options) {
        return Restrictions.sqlRestriction(sql);
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
        out.append(")");
    }

}
