package com.bee32.sem.frame.search;

import com.bee32.plover.criteria.hibernate.And;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class AndFragment
        extends SearchFragment {

    private static final long serialVersionUID = 1L;

    SearchFragment lhs;
    SearchFragment rhs;

    public AndFragment(SearchFragment lhs, SearchFragment rhs) {
        if (lhs == null)
            throw new NullPointerException("lhs");
        if (rhs == null)
            throw new NullPointerException("rhs");
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String getEntryLabel() {
        return lhs.getEntryLabel() + " 并且 " + rhs.getEntryLabel();
    }

    @Override
    public ICriteriaElement compose() {
        return new And(lhs.compose(), rhs.compose());
    }

}
