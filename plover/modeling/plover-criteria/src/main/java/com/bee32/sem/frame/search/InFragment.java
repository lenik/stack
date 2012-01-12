package com.bee32.sem.frame.search;

import java.util.Arrays;
import java.util.Collection;

import com.bee32.plover.criteria.hibernate.InArray;
import com.bee32.plover.criteria.hibernate.InCollection;

public class InFragment
        extends PropertyFragment {

    private static final long serialVersionUID = 1L;

    public InFragment(String propertyLabel, String propertyName, Object... propertyValues) {
        super(propertyLabel, Arrays.asList(propertyValues), //
                new InArray(propertyName, propertyValues));
    }

    public InFragment(String propertyLabel, String propertyName, Collection<?> propertyValues) {
        super(propertyLabel, propertyValues, //
                new InCollection(propertyName, propertyValues));
    }

}
