package com.bee32.sem.frame.search;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Not;

public class NotFragment
        extends SearchFragment {

    private static final long serialVersionUID = 1L;

    SearchFragment fragment;

    public NotFragment(SearchFragment fragment) {
        if (fragment == null)
            throw new NullPointerException("fragment");
        this.fragment = fragment;
    }

    @Override
    public String getEntryLabel() {
        return "不是: " + fragment.getEntryLabel();
    }

    @Override
    public ICriteriaElement compose() {
        ICriteriaElement element = fragment.compose();
        return Not.of(element);
    }

}
