package com.bee32.plover.criteria.hibernate;

import javax.free.Nullables;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class PropertyEquals
        extends Property2CriteriaElement {

    private static final long serialVersionUID = 1L;

    public PropertyEquals(String propertyName, String otherPropertyName) {
        super(propertyName, otherPropertyName);
    }

    @Override
    protected Criterion buildCriterion(int options) {
        return Restrictions.eqProperty(propertyName, otherPropertyName);
    }

    @Override
    protected boolean filterValue(Object lhs, Object rhs) {
        return Nullables.equals(lhs, rhs);
    }

    @Override
    protected String getOperator() {
        return "==";
    }

}
