package com.bee32.sem.frame.search;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.PropertyCriteriaElement;

public abstract class PropertyFragment
        extends SearchFragment {

    private static final long serialVersionUID = 1L;

    final String propertyLabel;
    final Object propertyValue;
    final PropertyCriteriaElement criteriaElement;

    public PropertyFragment(String propertyLabel, Object propertyValue, PropertyCriteriaElement criteriaElement) {
        this.propertyLabel = propertyLabel;
        this.propertyValue = propertyValue;
        this.criteriaElement = criteriaElement;
    }

    @Override
    public String getEntryLabel() {
        String operatorName = criteriaElement.getOperatorName();
        StringBuilder sb = new StringBuilder();
        sb.append(propertyLabel);
        sb.append(' ');
        sb.append(operatorName);
        sb.append(' ');
        sb.append(toSimpleString(propertyValue));
        return sb.toString();
    }

    @Override
    public ICriteriaElement compose() {
        return criteriaElement;
    }

}
