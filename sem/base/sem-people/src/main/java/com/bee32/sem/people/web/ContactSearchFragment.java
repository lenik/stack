package com.bee32.sem.people.web;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.frame.search.SearchFragment;
import com.bee32.sem.people.util.PeopleCriteria;

public class ContactSearchFragment extends SearchFragment {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    String pattern = "";

    public ContactSearchFragment(String numberPattern){
        this.pattern = numberPattern;
    }

    @Override
    public String getEntryLabel() {
        return "联系方式包含" +pattern;
    }

    @Override
    public ICriteriaElement compose() {

        return PeopleCriteria.withAnyNumberLike(pattern);
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

}
