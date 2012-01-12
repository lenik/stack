package com.bee32.sem.frame.search;

import com.bee32.plover.criteria.hibernate.LessOrEquals;

public class LessOrEqualsFragment
        extends PropertyFragment {

    private static final long serialVersionUID = 1L;

    public LessOrEqualsFragment(String propertyLabel, String propertyName, Object propertyValue) {
        super(propertyLabel, propertyValue, //
                new LessOrEquals(propertyName, propertyValue));
    }

}
