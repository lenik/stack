package com.bee32.sem.frame.search;

import com.bee32.plover.criteria.hibernate.GreaterOrEquals;

public class GreaterOrEqualsFragment
        extends PropertyFragment {

    private static final long serialVersionUID = 1L;

    public GreaterOrEqualsFragment(String propertyLabel, String propertyName, Object propertyValue) {
        super(propertyLabel, propertyValue, //
                new GreaterOrEquals(propertyName, propertyValue));
    }

}
