package com.bee32.sem.frame.search;

import java.util.Collection;

public interface ISearchFragmentsHolder {

    Collection<? extends SearchFragment> getSearchFragments();

    void addSearchFragment(SearchFragment fragment);

    void removeSearchFragment(SearchFragment fragment);

}
