package com.bee32.sem.chance.web;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.chance.util.ChanceCriteria;
import com.bee32.sem.frame.search.SearchFragment;

public class PlanSearchFragment extends SearchFragment {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    String pattern;
    boolean flag;

    PlanSearchFragment(String pattern, boolean flag) {
        this.pattern = pattern;
        this.flag = flag;
    }

    @Override
    public String getEntryLabel() {
        return pattern;
    }

    @Override
    public ICriteriaElement compose() {
        return ChanceCriteria.isPlan(flag);
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
