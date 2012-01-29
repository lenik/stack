package com.bee32.sem.frame.search;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Or;

public class OrFragment
        extends SearchFragment {

    private static final long serialVersionUID = 1L;

    SearchFragment lhs;
    SearchFragment rhs;

    public OrFragment(SearchFragment lhs, SearchFragment rhs) {
        if (lhs == null)
            throw new NullPointerException("lhs");
        if (rhs == null)
            throw new NullPointerException("rhs");
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String getEntryLabel() {
        return lhs.getEntryLabel() + " 或者 " + rhs.getEntryLabel();
    }

    @Override
    public ICriteriaElement compose() {
        return Or.of(lhs.compose(), rhs.compose());
    }

}
