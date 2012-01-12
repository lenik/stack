package com.bee32.sem.frame.search;

import com.bee32.plover.criteria.hibernate.NotEquals;

public class NotEqualsFragment
        extends PropertyFragment {

    private static final long serialVersionUID = 1L;

    public NotEqualsFragment(String propertyLabel, String propertyName, Object propertyValue) {
        super(propertyLabel, propertyValue, //
                new NotEquals(propertyName, propertyValue));
    }

}
