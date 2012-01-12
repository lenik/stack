package com.bee32.sem.frame.search;

import org.hibernate.criterion.MatchMode;

import com.bee32.plover.criteria.hibernate.Like;

public class LikeFragment
        extends PropertyFragment {

    private static final long serialVersionUID = 1L;

    public LikeFragment(String propertyLabel, String propertyName, String pattern, MatchMode matchMode) {
        this(propertyLabel, propertyName, pattern, false, matchMode);
    }

    public LikeFragment(String propertyLabel, String propertyName, String pattern, boolean ignoreCase,
            MatchMode matchMode) {
        super(propertyLabel, pattern, //
                new Like(ignoreCase, propertyName, pattern, matchMode));
    }

}
