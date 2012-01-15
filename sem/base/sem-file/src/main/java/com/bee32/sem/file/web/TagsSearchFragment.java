package com.bee32.sem.file.web;

import java.util.LinkedHashMap;
import java.util.Map;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.file.util.UserFileCriteria;
import com.bee32.sem.frame.search.SearchFragment;

public class TagsSearchFragment
        extends SearchFragment {

    private static final long serialVersionUID = 1L;

    Map<Integer, String> map = new LinkedHashMap<Integer, String>();

    @Override
    public String getEntryLabel() {
        StringBuilder sb = null;
        for (String tagName : map.values()) {
            if (sb == null)
                sb = new StringBuilder("带有标签");
            else
                sb.append(", ");
            sb.append(tagName);
        }
        return sb.toString();
    }

    @Override
    public ICriteriaElement compose() {
        return UserFileCriteria.withAnyTagIn(map.keySet());
    }

}
