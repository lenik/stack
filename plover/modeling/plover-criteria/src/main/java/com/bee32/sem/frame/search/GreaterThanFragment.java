package com.bee32.sem.frame.search;

import com.bee32.plover.criteria.hibernate.GreaterThan;

public class GreaterThanFragment
        extends PropertyFragment {

    private static final long serialVersionUID = 1L;

    public GreaterThanFragment(String propertyLabel, String propertyName, Object propertyValue) {
        super(propertyLabel, propertyValue, //
                new GreaterThan(propertyName, propertyValue));
    }

}
