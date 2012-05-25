package com.bee32.sem.frame.search;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class KeywordSearchParser {

    public static Collection<SearchFragment> parse(String propertyLabel, String propertyName, String q) {
        if (q == null)
            throw new NullPointerException("q");
        Set<SearchFragment> fragments = new LinkedHashSet<SearchFragment>();
        for (String keyword : q.split("\\s+")) {
            keyword = keyword.trim();
        }
        return fragments;
    }

}
