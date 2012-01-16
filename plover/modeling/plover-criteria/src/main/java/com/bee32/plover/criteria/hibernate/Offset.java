package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;
import org.springframework.expression.EvaluationContext;

public class Offset
        extends SpecialCriteriaElement {

    private static final long serialVersionUID = 1L;

    final int offset;

    /**
     * Set the first result to be retrieved.
     *
     * @param offset
     *            the first result to retrieve, numbered from <tt>0</tt>
     */
    public Offset(int offset) {
        this.offset = offset;
    }

    @Override
    public void apply(Criteria criteria, int options) {
        if ((options & NO_PAGINATION) != 0)
            return;
        if (offset != -1) {
            criteria.setFirstResult(offset);
        }
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
        out.append(")");
    }

}
