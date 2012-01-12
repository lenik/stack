package com.bee32.sem.frame.search;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class SearchFragmentWrapper
        extends SearchFragment {

    private static final long serialVersionUID = 1L;

    ICriteriaElement criteriaElement;
    String entryLabel;

    public SearchFragmentWrapper(ICriteriaElement criteriaElement, String entryLabel) {
        this.criteriaElement = criteriaElement;
        this.entryLabel = entryLabel;
    }

    @Override
    public String getEntryLabel() {
        return entryLabel;
    }

    @Override
    public ICriteriaElement compose() {
        return criteriaElement;
    }

}
