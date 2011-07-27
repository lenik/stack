package com.bee32.plover.criteria.hibernate;

import org.springframework.expression.Expression;

abstract class PropertyCriteriaElement
        extends SpelCriteriaElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;

    public PropertyCriteriaElement(String propertyName) {
        if (propertyName == null)
            throw new NullPointerException("propertyName");
        this.propertyName = propertyName;
    }

    @Override
    protected Expression compile() {
        return compile(propertyName);
    }

}
