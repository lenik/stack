package com.bee32.sem.frame.search;

import com.bee32.plover.criteria.hibernate.LessThan;

public class LessThanFragment
        extends PropertyFragment {

    private static final long serialVersionUID = 1L;

    public LessThanFragment(String propertyLabel, String propertyName, Object propertyValue) {
        super(propertyLabel, propertyValue, //
                new LessThan(propertyName, propertyValue));
    }

}
