package com.bee32.sem.frame.search;

import com.bee32.plover.criteria.hibernate.IsNotEmpty;

public class IsNotEmptyFragment
        extends PropertyFragment {

    private static final long serialVersionUID = 1L;

    public IsNotEmptyFragment(String propertyLabel, String propertyName) {
        super(propertyLabel, "空集", //
                new IsNotEmpty(propertyName));
    }

}
