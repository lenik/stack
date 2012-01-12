package com.bee32.sem.frame.search;

import com.bee32.plover.criteria.hibernate.IsNotNull;

public class IsNotNullFragment
        extends PropertyFragment {

    private static final long serialVersionUID = 1L;

    public IsNotNullFragment(String propertyLabel, String propertyName) {
        super(propertyLabel, "空", //
                new IsNotNull(propertyName));
    }

}
