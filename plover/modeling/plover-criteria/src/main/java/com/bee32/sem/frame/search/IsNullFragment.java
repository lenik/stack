package com.bee32.sem.frame.search;

import com.bee32.plover.criteria.hibernate.IsNull;

public class IsNullFragment
        extends PropertyFragment {

    private static final long serialVersionUID = 1L;

    public IsNullFragment(String propertyLabel, String propertyName) {
        super(propertyLabel, "空", //
                new IsNull(propertyName));
    }

}
