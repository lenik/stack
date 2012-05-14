package com.bee32.sem.inventory.web;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.frame.search.SearchFragment;
import com.bee32.sem.inventory.util.MaterialCriteria;

public class CategorySearchFragment extends SearchFragment {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected String pattern;

    public CategorySearchFragment(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getEntryLabel() {
        return "分类含有 " + pattern;
    }

    @Override
    public ICriteriaElement compose() {
        return MaterialCriteria.categoryLike(pattern);
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

}
