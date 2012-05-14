package com.bee32.sem.inventory.web;

import java.util.List;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.frame.search.SearchFragment;
import com.bee32.sem.inventory.entity.MaterialType;
import com.bee32.sem.inventory.util.MaterialCriteria;

public class TypeSearchFragment extends SearchFragment {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    String pattern;
    List<MaterialType> types;

    TypeSearchFragment(String pattern, List<MaterialType> types) {
        this.pattern = pattern;
        this.types = types;
    }

    @Override
    public String getEntryLabel() {
        return pattern;
    }

    @Override
    public ICriteriaElement compose() {
        return MaterialCriteria.categoryType(types);
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public List<MaterialType> getTypes() {
        return types;
    }

    public void setTypes(List<MaterialType> types) {
        this.types = types;
    }

}
