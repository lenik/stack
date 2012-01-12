package com.bee32.sem.frame.search;

import com.bee32.plover.criteria.hibernate.Between;

public class BetweenFragment
        extends PropertyFragment {

    private static final long serialVersionUID = 1L;

    public BetweenFragment(String propertyLabel, String propertyName, Object loValue, Object hiValue) {
        super(propertyLabel, loValue + " 到 " + hiValue, //
                new Between(propertyName, loValue, hiValue));
    }

}
