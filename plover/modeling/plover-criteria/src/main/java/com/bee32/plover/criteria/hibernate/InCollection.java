package com.bee32.plover.criteria.hibernate;

import java.util.Collection;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class InCollection
        extends PropertyCriteriaElement {

    private static final long serialVersionUID = 1L;

    final Collection<?> values;

    public InCollection(String propertyName, Collection<?> values) {
        super(propertyName);
        this.values = values;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.in(propertyName, values);
    }

    @Override
    protected boolean filterValue(Object val) {
        return values.contains(val);
    }

    @Override
    public String getOperator() {
        return "IN";
    }

    @Override
    public String getOperatorName() {
        return "属于集合";
    }

    @Override
    protected void formatValue(StringBuilder out) {
        FormatUtil.formatValue(out, values);
    }

}
