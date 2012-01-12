package com.bee32.sem.frame.search;

import com.bee32.plover.criteria.hibernate.IsEmpty;

public class IsEmptyFragment
        extends PropertyFragment {

    private static final long serialVersionUID = 1L;

    public IsEmptyFragment(String propertyLabel, String propertyName) {
        super(propertyLabel, "空集", //
                new IsEmpty(propertyName));
    }

}
