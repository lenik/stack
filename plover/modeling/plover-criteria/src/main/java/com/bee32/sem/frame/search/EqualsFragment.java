package com.bee32.sem.frame.search;

import com.bee32.plover.criteria.hibernate.Equals;

public class EqualsFragment
        extends PropertyFragment {

    private static final long serialVersionUID = 1L;

    public EqualsFragment(String propertyLabel, String propertyName, Object propertyValue) {
        super(propertyLabel, propertyValue, //
                new Equals(propertyName, propertyValue));
    }

}
