package com.bee32.plover.criteria.hibernate;

import java.util.Collection;

public abstract class SizeCriteriaElement
        extends PropertyCriteriaElement {

    private static final long serialVersionUID = 1L;

    protected final int size;

    public SizeCriteriaElement(String propertyName, int size) {
        super(propertyName);
        this.size = size;
    }

    @Override
    protected final boolean filterValue(Object val) {
        int size = 0;
        if (val instanceof Collection<?>) {
            Collection<?> coll = (Collection<?>) val;
            size = coll.size();
        }
        return filterSize(size);
    }

    protected abstract boolean filterSize(int sizeVar);

    @Override
    public abstract String getOperator();

    @Override
    public final void format(StringBuilder out) {
        out.append("(test-size ");
        out.append(propertyName);
        out.append(" ");
        out.append(getOperator());
        out.append(" ");
        formatValue(out);
        out.append(")");
    }

    @Override
    protected void formatValue(StringBuilder out) {
        out.append(size);
    }

}
