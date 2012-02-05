package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;
import org.springframework.expression.EvaluationContext;

public class Limit
        extends Offset {

    private static final long serialVersionUID = 1L;

    final int limit;

    /**
     * Set the first result to be retrieved, and set a fetch size for the underlying JDBC query.
     *
     * @param offset
     *            the first result to retrieve, numbered from <tt>0</tt>
     * @param fetchSize
     *            the fetch size
     */
    public Limit(int offset, int limit) {
        super(offset);
        this.limit = limit;
    }

    @Override
    public void apply(Criteria criteria, int options) {
        super.apply(criteria, options);
        if ((options & NO_PAGINATION) != 0)
            return;
        if (limit > 0)
            criteria.setMaxResults(limit);
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        // context.setlimit...?
        return true;
    }

    @Override
    public void format(StringBuilder out) {
        out.append("(offset ");
        out.append(offset);
        out.append(" limit ");
        out.append(limit);
        out.append(")");
    }

}
